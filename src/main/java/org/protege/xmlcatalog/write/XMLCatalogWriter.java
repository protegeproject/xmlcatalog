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
        if (catalog.getId() != null) {
            root.setAttribute(Handler.ID_ATTRIBUTE, catalog.getId());
        }
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
        /*
         * It is not clear how to set up the indent level in the output generated this 
         * way.  (These Transformers seem to be a lousy way of writing xml).  On google
         * it is suggested that you can use the "indent-number" attribute of the TransformerFactory
         * and for a long time this seemed to work.  But GForge ticket 2734 indicates that this 
         * setting can fail in a pretty brutal way.  See svn revision 20613 for the 
         * previous implementation.  Google also suggests an alternative slightly different 
         * variation for setting the indentation level but since I can't replicate GForge 2734 I
         * am suspicious as to whether this would work.
         */
        Transformer xformer = factory.newTransformer();
        xformer.setOutputProperty(OutputKeys.METHOD, "xml");
        xformer.setOutputProperty(OutputKeys.INDENT, "yes");
        xformer.transform(source, result);
    }
    
}
