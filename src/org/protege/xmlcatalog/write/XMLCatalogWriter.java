package org.protege.xmlcatalog.write;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.parser.Handler;

public class XMLCatalogWriter {
    public static String XML_CATALOG_NS="xmlns=\"urn:oasis:names:tc:entity:xmlns:xml:catalog\"";
    
    private XMLCatalog catalog;
    private Writer out;
    
    public XMLCatalogWriter(XMLCatalog catalog, Writer out) {
        this.catalog = catalog;
        this.out = out;
    }
    
    public void write() throws IOException {
        openDocument();
        closeDocument();
    }
    
    private void openDocument() throws IOException {
        String open = "<" + Handler.CATALOG_ELEMENT + " ";
        int attributeIndent = open.length();
        out.write(open);
        out.write(XML_CATALOG_NS);
        if (catalog.getId() != null) {
            out.write('\n');
            writeIndent(attributeIndent);
            out.write(Handler.ID_ATTRIBUTE );
            out.write(" = \"");
            out.write(catalog.getId());
            out.write('"');
        }
        if (catalog.getXmlBase() != null) {
            out.write('\n');
            writeIndent(attributeIndent);
            out.write(Handler.XML_BASE_ATTRIBUTE);
            out.write(" = \"");
            out.write(catalog.getXmlBase().toString());
            out.write('"');
        }
        if (catalog.getPrefer() != null) {
            out.write("\n");
            writeIndent(attributeIndent);
            out.write(Handler.PREFER_ATTRIBUTE);
            out.write(" = \"");
            switch (catalog.getPrefer()) {
            case PUBLIC:
                out.write(Handler.PREFER_PUBLIC_VALUE);
                break;
            case SYSTEM:
                out.write(Handler.PREFER_SYSTEM_VALUE);
                break;
            }
            out.write('"');
          
        }
    }
    
    private void closeDocument() {
        
    }
    
    private void writeIndent(int indent) {
        
    }

}
