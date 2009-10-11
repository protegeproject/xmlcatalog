package org.protege.xmlcatalog.parser;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.Stack;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.XMLCatalog.Prefer;
import org.protege.xmlcatalog.entry.Entry;
import org.protege.xmlcatalog.entry.GroupEntry;
import org.protege.xmlcatalog.entry.PublicEntry;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler extends DefaultHandler {
    public final static String GROUP_ATTRIBUTE = "group";
    public final static String URI_ATTRIBUTE   = "uri";

    private XMLCatalog catalog;
    private Stack<GroupEntry> groupStack = new Stack<GroupEntry>();
    
    public static XMLCatalog parseDocument(URL catalog) throws IOException, ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        Handler handler = new Handler();
        InputStream is = null;
        is = catalog.openStream();
        SAXParser parser = factory.newSAXParser();
        parser.parse(is, handler);
        return handler.getCatalog();
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("catalog")) {
            catalog = new XMLCatalog(getId(attributes), getPrefer(attributes), getXmlBase(attributes));
        }
        else if (qName.equals(GROUP_ATTRIBUTE)) {
            GroupEntry group = new GroupEntry(getId(attributes), getPrefer(attributes),  getXmlBase(attributes));
            groupStack.push(group);
            addEntry(group);
        }
        else if (qName.equals("public")) {
            addEntry(new PublicEntry(getId(attributes), 
                                     attributes.getValue("publicId"), 
                                     URI.create(attributes.getValue(URI_ATTRIBUTE)),
                                     getXmlBase()));
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(GROUP_ATTRIBUTE)) {
            groupStack.pop();
        }
    }

    public XMLCatalog getCatalog() {
        return catalog;
    }
    
    private void addEntry(Entry entry) {
        if (groupStack.isEmpty()) {
            catalog.addEntry(entry);
        }
        else {
            groupStack.peek().addEntry(entry);
        }
    }
    
    private String getId(Attributes attributes) {
        return attributes.getValue("id");
    }
    
    private Prefer getPrefer() {
        if (groupStack.isEmpty()) {
            return catalog.getPrefer();
        }
        else {
            return groupStack.peek().getPrefer();
        }
    }
    
    private Prefer getPrefer(Attributes attributes) {
        String preferString = attributes.getValue("prefer");
        if (preferString == null) {
            return getPrefer();
        }
        else if (preferString.toLowerCase().equals("public")) {
            return Prefer.PUBLIC;
        }
        else if (preferString.toLowerCase().equals("system")) {
            return Prefer.SYSTEM;
        }
        else  {
            return null;
        }
    }
    
    private URI getXmlBase() {
        if (groupStack.isEmpty()) {
            return catalog.getXmlBase();
        }
        else {
            return groupStack.peek().getXmlBase();
        }
    }
    
    private URI getXmlBase(Attributes attributes) {
        String baseString = attributes.getValue("xml:base");
        if (baseString != null) {
            return URI.create(baseString);
        }
        else return getXmlBase();
    }
}
