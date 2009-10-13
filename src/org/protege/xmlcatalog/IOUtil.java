package org.protege.xmlcatalog;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.protege.xmlcatalog.parser.Handler;
import org.xml.sax.SAXException;

public class IOUtil {
    
    public static XMLCatalog parseDocument(URL catalog) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        Handler handler = new Handler();
        InputStream is = null;
        is = catalog.openStream();
        SAXParser parser = factory.newSAXParser();
        parser.parse(is, handler);
        return handler.getCatalog();
    }
}
