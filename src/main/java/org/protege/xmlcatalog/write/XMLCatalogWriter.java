package org.protege.xmlcatalog.write;

import java.io.IOException;
import java.io.Writer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.entry.Entry;
import org.protege.xmlcatalog.parser.Handler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class XMLCatalogWriter {
    public static String XML_CATALOG_NS="urn:oasis:names:tc:entity:xmlns:xml:catalog";
    
    private XMLCatalog catalog;
    private Writer out;
    
    public XMLCatalogWriter(XMLCatalog catalog, Writer out) {
        this.catalog = catalog;
        this.out = out;
    }
    
    public void write() throws IOException, ParserConfigurationException, TransformerFactoryConfigurationError, TransformerException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = dbf.newDocumentBuilder();
        Document document = builder.newDocument();
        Element root = document.createElementNS(XML_CATALOG_NS, Handler.CATALOG_ELEMENT);
        if (catalog.getXmlBase() != null) {
            root.setAttribute(Handler.XML_BASE_ATTRIBUTE, catalog.getXmlBase().toString());
        }
        if (catalog.getPrefer() != null) {
            root.setAttribute(Handler.PREFER_ATTRIBUTE, catalog.getPrefer().getName());
        }
        document.appendChild(root);
        
        XMLRenderingVisitor renderer = new XMLRenderingVisitor(document, root, CatalogUtilities.resolveXmlBase(catalog));
        for (Entry entry : catalog.getEntries()) {
            entry.accept(renderer);
        }

        save(document);
    }
    
    private void save(Document document) throws TransformerFactoryConfigurationError, TransformerException {
        Source source = new DOMSource(document);
        Result result = new StreamResult(out);
        TransformerFactory factory = TransformerFactory.newInstance();
        /* Hopefully this guards against residual effects from GForge ticket 2734 */
        try {
            factory.setAttribute("indent-number", 4);
        }
        catch (IllegalArgumentException e) {
        	// ok - be that way.
        }
        Transformer xformer = factory.newTransformer();
        xformer.setOutputProperty(OutputKeys.METHOD, "xml");
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        xformer.transform(source, result);
    }
    
}
