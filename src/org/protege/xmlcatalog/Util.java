package org.protege.xmlcatalog;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.protege.xmlcatalog.parser.Handler;

public class Util {
    
    public static XMLCatalog parseDocument(URL catalog, URI xmlbase) throws IOException {
        try {
            if (xmlbase == null) {
                xmlbase = catalog.toURI();
            }
            SAXParserFactory factory = SAXParserFactory.newInstance();
            Handler handler = new Handler();
            InputStream is = null;
            is = catalog.openStream();
            SAXParser parser = factory.newSAXParser();
            parser.parse(is, handler);
            return handler.getCatalog();
        }
        catch (IOException ioe) {
            throw ioe;
        }
        catch (Exception e) {
            IOException ioe = new IOException(e.getMessage());
            ioe.initCause(e);
            throw ioe;
        }
    }

    public static URI resolveUri(URI relative, XmlBaseContext context) {
        URI xmlbase = resolveXmlBase(context);
        if (xmlbase == null) {
            return relative;
        }
        else {
            return xmlbase.resolve(relative);
        }
    }
    
    public static URI resolveXmlBase(XmlBaseContext context) {
        URI xmlbase = null;
        while (xmlbase == null && context != null) {
            xmlbase = context.getXmlBase();
            context = context.getXmlBaseContext();
        }
        return xmlbase;
    }
}
