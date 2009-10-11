package org.protege.xmlcatalog;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.protege.xmlcatalog.parser.Handler;
import org.xml.sax.SAXException;

public class TestCatalog extends TestCase {

    public void testExample1() throws MalformedURLException, IOException, ParserConfigurationException, SAXException {
        Handler.parseDocument(new File("examples/Example1.xml").toURL());
    }
}
