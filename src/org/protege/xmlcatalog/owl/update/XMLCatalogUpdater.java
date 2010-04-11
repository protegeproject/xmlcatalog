package org.protege.xmlcatalog.owl.update;

import java.io.File;
import java.net.URI;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.Prefer;
import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.XmlBaseContext;
import org.protege.xmlcatalog.entry.Entry;
import org.protege.xmlcatalog.entry.GroupEntry;
import org.protege.xmlcatalog.entry.UriEntry;

/**
 * This class manages algorithms that try to populate an xmlcatalog for a directory.
 * Essentially the problem is to examine the contents of the ontologies in the directory
 * and to guess where they could have come from on the internet.  Note that any such algorithm
 * must be a heuristic.  But several such heuristics are available including using the xmlbase
 * or using the ontology id.
 * 
 * @author tredmond
 *
 */
public class XMLCatalogUpdater {
    private static Logger log = Logger.getLogger(XMLCatalogUpdater.class);
    
    private Set<Algorithm> algorithms;
    private String groupName;
    
    public void setAlgorithms(Set<Algorithm> algorithms) {
        this.algorithms = new HashSet<Algorithm>(algorithms);
    }
    
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public boolean update(File folder, GroupEntry ge, long catalogDate) {
		boolean modified  = removeStaleEntries(ge, catalogDate);
    	if (algorithms == null || !algorithms.isEmpty()) {
    		return modified;
    	}
    	Map<URI, Set<File>> importToDiskLocationMap = new HashMap<URI, Set<File>>();
    	for (File physicalLocation : folder.listFiles()) {
    		if (physicalLocation.exists() 
    				&& physicalLocation.isFile() 
    				&& physicalLocation.getPath().endsWith(".owl")
    				&& !physicalLocation.getName().startsWith(".")
    				&& (physicalLocation.lastModified() >= catalogDate)) {
    			for (Algorithm algorithm : algorithms) {
    				Set<URI> webLocations = algorithm.getSuggestions(physicalLocation);
    				for (URI webLocation : webLocations) {
    					Set<File> diskLocations = importToDiskLocationMap.get(webLocation);
    					if (diskLocations == null) {
    						diskLocations = new HashSet<File>();
    						importToDiskLocationMap.put(webLocation, diskLocations);
    					}
    					diskLocations.add(physicalLocation);
    				}
    			}
    		}
    	}
    	for (java.util.Map.Entry<URI, Set<File>> entry : importToDiskLocationMap.entrySet()) {
    		URI webLocation = entry.getKey();
    		Set<File> diskLocations = entry.getValue();
    		if (diskLocations == null || diskLocations.isEmpty()) {
    			continue;
    		}
    		for (Entry e : ge.getEntries()) {
    			if (e instanceof UriEntry) {
    				UriEntry ue = (UriEntry) e;
    				if (ue.getName().equals(webLocation.toString())) {
    					ge.removeEntry(ue);
    					modified = true;
    				}
    			}
    		}
    		boolean duplicatesFound = (diskLocations.size() != 1);
    		for (File physicalLocation : diskLocations) {
    			URI shortLocation = folder.toURI().relativize(physicalLocation.toURI());
    			UriEntry u = new UriEntry(null, 
    					                  ge, 
    					                  (duplicatesFound ? "duplicate:" : "") + webLocation.toString(),
    					                  shortLocation,
    					                  null);
    			modified = true;
    		}
    	}
    	return modified;
    }
    
    private boolean removeStaleEntries(GroupEntry ge, long catalogDate) {
    	boolean removed = false;
        for (Entry e : ge.getEntries()) {
            if (e instanceof UriEntry) {
                UriEntry ue = (UriEntry) e;
                try {
                    File f = new File(ue.getAbsoluteURI());
                    if (!f.exists() || f.lastModified() > catalogDate) {
                        ge.removeEntry(ue);
                        removed = true;
                    }
                }
                catch (Throwable t) {
                    ;
                }
            }
        }
        return removed;
    }
    
    private GroupEntry findGroupEntry(XMLCatalog catalog, File catalogFile) {
        for (Entry e : catalog.getEntries()) {
            if (e instanceof GroupEntry && e.getId() != null && e.getId().startsWith(groupName)) {
                return (GroupEntry) e;
            }
        }
        File directory = catalogFile.getParentFile();
        URI xmlBase = directory != null ? directory.toURI() : null;
        GroupEntry ge = new GroupEntry(groupName, catalog, Prefer.PUBLIC, xmlBase);
        catalog.addEntry(ge);
        return ge;
    }
    
}
