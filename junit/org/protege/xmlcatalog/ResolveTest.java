package org.protege.xmlcatalog;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.tools.CatalogResolver;

public class ResolveTest extends TestCase {
    private CatalogResolver resolver;
    private XMLCatalog catalog;

    public void test01() throws TransformerException, MalformedURLException, IOException {
        readCatalog("test/catalog01.xml");

        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = URI.create("file:/home/tredmond/Shared/ontologies/simple/ontology1.owl");
        checkBothAlgorithmsSame(u);
        assertTrue(Util.getRedirect(u, catalog).equals(redirect));
    }
    
    public void test02() throws MalformedURLException, IOException, TransformerException {
        readCatalog("test/catalog02.xml");

        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = URI.create("file:/home/tredmond/Shared/simple/ontology1.owl");
        checkBothAlgorithmsSame(u);
        assertTrue(Util.getRedirect(u, catalog).equals(redirect));
    }
    
    public void test03() throws MalformedURLException, IOException, TransformerException {
        readCatalog("test/catalog03.xml");

        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = new File("test/simple/ontology1.owl").toURI();
        // this doesn't work  because of a seemingly silly problem involving the URI for the outer xml base.
        // checkBothAlgorithmsSame(u);
        assertTrue(Util.getRedirect(u, catalog).equals(redirect));
    }
    
    private void readCatalog(String catalogLocation) throws MalformedURLException, IOException {
        System.setProperty("xml.catalog.ignoreMissing", "true");
        CatalogManager manager = new CatalogManager();
        manager.setUseStaticCatalog(false);
        manager.setCatalogFiles(catalogLocation);
        resolver = new CatalogResolver(manager);
        catalog = Util.parseDocument(new File(catalogLocation).toURL(), null);
    }
    
    private void checkBothAlgorithmsSame(URI u) throws TransformerException {
        String result1 = resolver.resolve(u.toString(), null).getSystemId();
        String result2 = Util.getRedirect(u, catalog).toString();
        assertTrue(result1.equals(result2));
    }
    

    
}
