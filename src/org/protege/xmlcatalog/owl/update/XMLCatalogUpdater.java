package org.protege.xmlcatalog.owl.update;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.entry.UriEntry;

public class XMLCatalogUpdater {
    private static Logger log = Logger.getLogger(XMLCatalogUpdater.class);
    
    private Set<Algorithm> algorithms;
    
    public void setAlgorithms(Set<Algorithm> algorithms) {
        this.algorithms = new HashSet<Algorithm>(algorithms);
    }

    public XMLCatalog update(File catalogFile) throws IOException {
		XMLCatalog catalog;
		File folder = catalogFile.getParentFile();
		if (catalogFile.exists()) {
			catalog = CatalogUtilities.parseDocument(catalogFile.toURI().toURL());
		}
		else {
			catalog = new XMLCatalog(folder.toURI());
		}
    	if (algorithms != null && !algorithms.isEmpty()) {
    		boolean changed = false;
    		long catalogDate = catalogFile.lastModified();


    		for (File physicalLocation : folder.listFiles()) {
    			if (physicalLocation.exists() 
    					&& physicalLocation.isFile() 
    					&& physicalLocation.getPath().endsWith(".owl")
    					&& !physicalLocation.getName().startsWith(".")
    					&& (!catalogFile.exists() || physicalLocation.lastModified() >= catalogDate)) {
    				for (Algorithm algorithm : algorithms) {
    					Set<URI> webLocations = algorithm.getSuggestions(physicalLocation);
    					for (URI webLocation : webLocations) {
    						if (CatalogUtilities.getRedirect(webLocation, catalog) == null) {
    						    URI shortLocation = folder.toURI().relativize(physicalLocation.toURI());
    							UriEntry u = new UriEntry(null, 
    							                          catalog, 
    							                          webLocation.toString(),
    							                          shortLocation,
    							                          null);
    							catalog.addEntry(u);
    							changed = true;
    						}
    						else {
    							log.info("Location with duplicate redirects found " + webLocation);
    						}
    					}
    				}
    			}
            }
            if (changed) {
                CatalogUtilities.save(catalog, catalogFile);
            }
        }
        return catalog;

    }
    
}
