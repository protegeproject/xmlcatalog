package org.protege.xmlcatalog.owl.update;

import java.io.File;
import java.net.URI;

import junit.framework.TestCase;

public class XmlBaseTest extends TestCase {
    public static final String PIZZA_NAME = "http://www.co-ode.org/ontologies/pizza/2005/10/18/pizza.owl";

    public void testOwlXml() {
        File pizza = new File("test/pizza-xml.owl");
        XmlBaseAlgorithm algorithm = new XmlBaseAlgorithm();
        assertTrue(algorithm.getSuggestions(pizza).size() == 1);
        assertTrue(algorithm.getSuggestions(pizza).contains(URI.create(PIZZA_NAME)));
    }
    
    public void testRdfXml() {
        File pizza = new File("test/pizza.owl");
        XmlBaseAlgorithm algorithm = new XmlBaseAlgorithm();
        assertTrue(algorithm.getSuggestions(pizza).size() == 1);
        assertTrue(algorithm.getSuggestions(pizza).contains(URI.create(PIZZA_NAME)));
    }
}
