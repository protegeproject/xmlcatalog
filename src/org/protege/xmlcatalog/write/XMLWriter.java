package org.protege.xmlcatalog.write;

import java.io.IOException;
import java.io.Writer;

import org.protege.xmlcatalog.parser.Handler;

public class XMLWriter {
    private Writer out;
    private boolean firstAttribute = true;
    private int indent;
    private int attributeIndent;
    
    public XMLWriter(Writer out, int indent) {
        this.out = out;
        this.indent = indent;
    }
    
    public int getIndent() {
        return indent;
    }

    public void setIndent(int indent) {
        this.indent = indent;
    }

    public void startElement(String element) throws IOException {
        writeIndent(indent);
        String open = "<" + element + " ";
        attributeIndent = open.length();
        firstAttribute = true;
        out.write(open);
    }
    
    public void writeAttribute(String attribute, String value) throws IOException {
        if (!firstAttribute) {
            out.write('\n');
            writeIndent(attributeIndent);
        }
        else {
            firstAttribute = false;
        }
        out.write(attribute);
        out.write(" = \"");
        out.write(value);
        out.write('"');
    }
    
    public void endElement() {
        out.write("/>");
    }
    
    private void writeIndent(int indent) throws IOException {
        while (indent-- >= 0) {
            out.write(' ');
        }
    }
}
