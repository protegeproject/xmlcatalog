package org.protege.xmlcatalog.owl.update;

import java.io.File;
import java.net.URI;

import junit.framework.TestCase;

public class RdfXmlNameTestCase extends TestCase {

    public void testSimple() {
        File pizza = new File("test/pizza.owl");
        RdfXmlNameAlgorithm algorithm = new RdfXmlNameAlgorithm();
        assertTrue(algorithm.getSuggestions(pizza).size() == 1);
        assertTrue(algorithm.getSuggestions(pizza).contains(URI.create(XmlBaseTest.PIZZA_NAME)));
    }
    
    public void testAmbiguous() {
        File ambiguous = new File("test/Ambiguous.owl");
        RdfXmlNameAlgorithm algorithm = new RdfXmlNameAlgorithm();
        assertTrue(algorithm.getSuggestions(ambiguous).size()==1);
        assertTrue(algorithm.getSuggestions(ambiguous).contains(URI.create("http://www.test.com/right.owl")));
    }
    
    public void testVersioned() {
        String name = "http://www.tigraworld.com/protege/determinant.owl";
        String version = "http://www.tigraworld.com/protege/determinant-2007-08-01.owl";
        File versioned = new File("test/versioned.owl");
        RdfXmlNameAlgorithm algorithm = new RdfXmlNameAlgorithm();
        assertTrue(algorithm.getSuggestions(versioned).size() == 1);
        assertTrue(algorithm.getSuggestions(versioned).contains(URI.create(version)));
        
        algorithm.setAssumeLatest(true);
        assertTrue(algorithm.getSuggestions(versioned).size() == 2);
        assertTrue(algorithm.getSuggestions(versioned).contains(URI.create(name)));
        assertTrue(algorithm.getSuggestions(versioned).contains(URI.create(version)));
    }
}
