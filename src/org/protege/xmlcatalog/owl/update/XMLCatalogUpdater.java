package org.protege.xmlcatalog.owl.update;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.Prefer;
import org.protege.xmlcatalog.XMLCatalog;
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

    public XMLCatalog update(File catalogFile) throws IOException {
		XMLCatalog catalog;
		File folder = catalogFile.getParentFile();
		Set<URI> locationsAdded = new HashSet<URI>();
		Set<URI> alreadyWarnedOfDuplicate = new HashSet<URI>();
		if (catalogFile.exists()) {
			catalog = CatalogUtilities.parseDocument(catalogFile.toURI().toURL());
		}
		else {
			catalog = new XMLCatalog(folder.toURI());
		}
		GroupEntry ge = findGroupEntry(catalog, catalogFile);
        long catalogDate = catalogFile.lastModified();
		removeStaleEntries(ge, catalogDate);
    	if (algorithms != null && !algorithms.isEmpty()) {

    		for (File physicalLocation : folder.listFiles()) {
    			if (physicalLocation.exists() 
    					&& physicalLocation.isFile() 
    					&& physicalLocation.getPath().endsWith(".owl")
    					&& !physicalLocation.getName().startsWith(".")
    					&& (!catalogFile.exists() || physicalLocation.lastModified() >= catalogDate)) {
    				for (Algorithm algorithm : algorithms) {
    					Set<URI> webLocations = algorithm.getSuggestions(physicalLocation);
    					for (URI webLocation : webLocations) {
    					    if (locationsAdded.contains(webLocation) && !alreadyWarnedOfDuplicate.contains(webLocation)) {
                                log.info("Multiple physical locations mapping to the import declaration " + webLocation);
                                log.info("Some possibilies may be skipped");
                                alreadyWarnedOfDuplicate.add(webLocation);
                                break;
    					    }
    					    locationsAdded.add(webLocation);
    					    for (Entry e : ge.getEntries()) {
    					        if (e instanceof UriEntry) {
    					            UriEntry ue = (UriEntry) e;
    					            if (ue.getName().equals(webLocation.toString())) {
    					                ge.removeEntry(ue);
    					            }
    					        }
    					    }
    					    URI shortLocation = folder.toURI().relativize(physicalLocation.toURI());
    					    UriEntry u = new UriEntry(null, 
    					                              catalog, 
    					                              webLocation.toString(),
    					                              shortLocation,
    					                              null);
    					    catalog.addEntry(u);
    					}
    				}
    			}
            }
    		CatalogUtilities.save(catalog, catalogFile);
        }
        return catalog;

    }
    
    private void removeStaleEntries(GroupEntry ge, long catalogDate) {
        for (Entry e : ge.getEntries()) {
            if (e instanceof UriEntry) {
                UriEntry ue = (UriEntry) e;
                try {
                    File f = new File(ue.getAbsoluteURI());
                    if (!f.exists() || f.lastModified() > catalogDate) {
                        ge.removeEntry(ue);
                    }
                }
                catch (Throwable t) {
                    ;
                }
            }
        }
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
