package org.protege.xmlcatalog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.TransformerException;

import org.protege.xmlcatalog.entry.Entry;
import org.protege.xmlcatalog.exception.CatalogParseException;
import org.protege.xmlcatalog.parser.Handler;
import org.protege.xmlcatalog.redirect.UriRedirectVisitor;
import org.protege.xmlcatalog.write.XMLCatalogWriter;
import org.xml.sax.InputSource;

public class CatalogUtilities {
    
    public static XMLCatalog parseDocument(URL catalog) throws IOException {
        try {
            URI xmlbase = catalog.toURI();
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            Handler handler = new Handler(xmlbase);
            InputSource is = new InputSource(catalog.openStream());
            is.setEncoding("UTF-8");  // is this necessary?
            SAXParser parser = factory.newSAXParser();
            parser.parse(is, handler);
            return handler.getCatalog();
        }
        catch (Exception e) {
        	throw new CatalogParseException(e);
        }
    }
    
    public static void save(XMLCatalog catalog, File f) throws IOException {
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(f), "UTF-8");
        XMLCatalogWriter xwriter = new XMLCatalogWriter(catalog, writer);
        try {
            xwriter.write();
            writer.flush();
            writer.close();
        }
        catch (ParserConfigurationException pce) {
            IOException ioe = new IOException("Error writing catalog to file " + f);
            ioe.initCause(pce);
            throw ioe;
        }
        catch (TransformerException te) {
            IOException ioe = new IOException("Error writing catalog to file " + f);
            ioe.initCause(te);
            throw ioe;  
        }
    }
    
    public static URI getRedirect(URI original, XMLCatalog catalog) {
        UriRedirectVisitor visitor = new UriRedirectVisitor(original);
        for (Entry subEntry : catalog.getEntries()) {
            subEntry.accept(visitor);
        }
        return visitor.getRedirect() == null ? null : visitor.getRedirect();
    }
    

    public static URI resolveXmlBase(XmlBaseContext context) {
        URI xmlbase = null;
        while (xmlbase == null && context != null) {
            xmlbase = context.getXmlBase();
            context = context.getXmlBaseContext();
        }
        if (!xmlbase.isAbsolute()) {
            URI outerBase = resolveXmlBase(context);
            xmlbase = outerBase.resolve(xmlbase);
        }
        return xmlbase;
    }
    
    public static URI relativize(URI u, XmlBaseContext context) {
        URI xmlbase = resolveXmlBase(context);
        if (xmlbase == null) {
            return u;
        }
        String path = xmlbase.getPath();
        int index;
        if (path != null && (index = path.lastIndexOf("/")) != -1) {
            path = path.substring(0, index + 1);
            try {
                xmlbase = new URI(xmlbase.getScheme(), xmlbase.getUserInfo(), xmlbase.getHost(), xmlbase.getPort(),
                                  path, null, null);
            }
            catch (URISyntaxException e) {
                ;
            }
        }
        return xmlbase.relativize(u);
    }
}
