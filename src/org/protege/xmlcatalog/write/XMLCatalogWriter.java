package org.protege.xmlcatalog.write;

import java.io.IOException;
import java.io.Writer;

import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.parser.Handler;

public class XMLCatalogWriter {
    public static String XML_CATALOG_NS="xmlns=\"urn:oasis:names:tc:entity:xmlns:xml:catalog\"";
    
    private XMLCatalog catalog;
    private XMLWriter out;
    private Writer out;
    
    public XMLCatalogWriter(XMLCatalog catalog, Writer out) {
        this.catalog = catalog;
        this.out = new XMLWriter(out, 0);
    }
    
    public void write() throws IOException {
        openDocument();
        closeDocument();
    }
    
    private void openDocument() throws IOException {
        out.startElement(XML_CATALOG_NS);
        if (catalog.getId() != null) {
            out.writeAttribute(Handler.ID_ATTRIBUTE, catalog.getId());
        }
        if (catalog.getXmlBase() != null) {
            out.writeAttribute(Handler.XML_BASE_ATTRIBUTE, catalog.getXmlBase().toString());
        }
        if (catalog.getPrefer() != null) {
            String p = null;
            switch (catalog.getPrefer()) {
            case PUBLIC:
                p = Handler.PREFER_PUBLIC_VALUE;
                break;
            case SYSTEM:
                p = Handler.PREFER_SYSTEM_VALUE;
                break;
            }
            out.writeAttribute(Handler.PREFER_ATTRIBUTE, p);
        }
    }
    
    private void closeDocument() {
        out.endElement();
    }
    


}
