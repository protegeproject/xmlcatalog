package org.protege.xmlcatalog;

import java.io.File;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

public class CatalogTest extends TestCase {
    private static final Logger log = Logger.getLogger(CatalogTest.class);

    public void testExample1() {
        try {
            CatalogUtilities.parseDocument(new File("test/Example1.xml").toURI().toURL());
        }
        catch (Throwable t) {
            log.warn("Test failed", t);
            fail();
        }
    }
    
    public void testExample2() {
        try {
            CatalogUtilities.parseDocument(new File("test/Example2.xml").toURI().toURL());
        }
        catch (Throwable t) {
            log.warn("Test failed", t);
            fail();
        }
    }
    
    public void testCatalog8() {
        try {
            CatalogUtilities.parseDocument(new File("test/catalog08.xml").toURI().toURL());
        }
        catch (Throwable t) {
            log.warn("Test failed", t);
            fail();
        }
    }
}
