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
        readCatalog("src/test/resources/catalog01.xml");

        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = URI.create("file:/home/tredmond/Shared/ontologies/simple/ontology1.owl");
        checkBothAlgorithmsSame(u, false);
        assertTrue(CatalogUtilities.getRedirect(u, catalog).equals(redirect));
    }
    
    public void test02() throws MalformedURLException, IOException, TransformerException {
        readCatalog("src/test/resources/catalog02.xml");

        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = URI.create("file:/home/tredmond/Shared/simple/ontology1.owl");
        checkBothAlgorithmsSame(u, false);
        assertTrue(CatalogUtilities.getRedirect(u, catalog).equals(redirect));
    }
    
    public void test03() throws MalformedURLException, IOException, TransformerException {
        readCatalog("src/test/resources/catalog03.xml");

        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = new File("src/test/resources/simple/ontology1.owl").toURI();
        // needs trim  because of a seemingly silly problem involving the URI for the outer xml base.
        checkBothAlgorithmsSame(u, true);
        assertTrue(CatalogUtilities.getRedirect(u, catalog).equals(redirect));
    }
    
    public void test04() throws MalformedURLException, IOException, TransformerException {
        readCatalog("src/test/resources/catalog04.xml");
        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = new File("src/test/resources/dir1/dir2/simple/ontology1.owl").toURI();
        // The apache resolver behaves differently in Java 1.5 and Java 1.6
        // I have aligned this library with the 1.6 behavior
        if (!JunitUtilities.isJava5()) {
            checkBothAlgorithmsSame(u, true);
        }
        assertTrue(CatalogUtilities.getRedirect(u, catalog).equals(redirect));
    }
    
    public void test05() throws MalformedURLException, IOException, TransformerException {
        readCatalog("src/test/resources/catalog05.xml");
        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = new File("src/test/resources/dir1/dir2/simple/ontology1.owl").toURI();
        // The apache resolver behaves differently in Java 1.5 and Java 1.6
        // I have aligned this library with the 1.6 behavior
        if (!JunitUtilities.isJava5()) {
            checkBothAlgorithmsSame(u, true);
        }
        assertTrue(CatalogUtilities.getRedirect(u, catalog).equals(redirect));
    }
    
    public void test06() throws MalformedURLException, IOException, TransformerException {
        readCatalog("src/test/resources/catalog06.xml");
        URI u = URI.create("http://www.co-ode.org/ontologies/pizza/pizza.owl");
        URI redirect = URI.create("file:/home/tredmond/Shared/ontologies/protege/pizza.owl");
        assertTrue(CatalogUtilities.getRedirect(u, catalog).equals(redirect));
    }
    
    public void test07() throws TransformerException, MalformedURLException, IOException {
        readCatalog("src/test/resources/catalog07.xml");
        doTest07();

    }
    
    public void test08() throws TransformerException, MalformedURLException, IOException {
        readCatalog("src/test/resources/catalog08.xml");
        doTest07();
    }
    
    public void test09() throws TransformerException, MalformedURLException, IOException {
        readCatalog("src/test/resources/catalog09.xml");
        doTest07();
    }    
    
    private void doTest07() throws TransformerException {
        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = URI.create("file:/home/tredmond/Shared/ontologies/simple/ontology1.owl");
        checkBothAlgorithmsSame(u, false);
        assertTrue(CatalogUtilities.getRedirect(u, catalog).equals(redirect));
    }
    
    private void readCatalog(String catalogLocation) throws MalformedURLException, IOException {
        System.setProperty("xml.catalog.ignoreMissing", "true");
        CatalogManager manager = new CatalogManager();
        manager.setUseStaticCatalog(false);
        manager.setCatalogFiles(catalogLocation);
        resolver = new CatalogResolver(manager);
        catalog = parseCatalog(new File(catalogLocation));
    }
    
    protected XMLCatalog parseCatalog(File location) throws MalformedURLException, IOException {
        return CatalogUtilities.parseDocument(location.toURI().toURL());
    }
    
    private void checkBothAlgorithmsSame(URI u, boolean trim) throws TransformerException {
        String result1 = resolver.resolve(u.toString(), null).getSystemId();
        String result2 = CatalogUtilities.getRedirect(u, catalog).toString();
        if (trim) {
            result1 = trimFileScheme(result1);
            result2 = trimFileScheme(result2);
        }
        assertTrue(result1.equals(result2));
    }
    
    /*
     * This is a hack to avoid losing test cases because of differences like
     *   file:/foo vs. file://foo.
     * I am not sure why these differences come up and am not sure if the second 
     * version makes sense.
     */
    private String trimFileScheme(String uri) {
        String filePrefix="file:";
        if (uri.startsWith(filePrefix)) {
            uri = uri.substring(filePrefix.length());
            while (uri.startsWith("/")) {
                uri = uri.substring(1);
            }
        }
        return uri;
    }
    
}
