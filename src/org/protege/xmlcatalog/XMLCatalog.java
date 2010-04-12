package org.protege.xmlcatalog;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.protege.xmlcatalog.entry.Entry;
import org.protege.xmlcatalog.parser.OuterXmlBaseContext;

public class XMLCatalog implements XmlBaseContext {
    private String id;
    private XmlBaseContext context;
    private Prefer prefer;
    private URI xmlBase;
    private List<Entry> entries = new ArrayList<Entry>();
    
    public XMLCatalog(URI xmlBase) {
    	this(null, new OuterXmlBaseContext(xmlBase), Prefer.PUBLIC, null);
    }
    
    public XMLCatalog(String id, XmlBaseContext context, Prefer prefer, URI xmlBase) {
        this.id = id;
        this.context = context;
        this.prefer = prefer;
        this.xmlBase = xmlBase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public XmlBaseContext getXmlBaseContext() {
        return context;
    }
    
    public void setXmlBaseContext(XmlBaseContext context) {
        this.context = context;
    }

    public Prefer getPrefer() {
        return prefer;
    }

    public void setPrefer(Prefer prefer) {
        this.prefer = prefer;
    }

    public URI getXmlBase() {
        return xmlBase;
    }

    public void setXmlBase(URI xmlBase) {
        this.xmlBase = xmlBase;
    }

    public List<Entry> getEntries() {
        return new ArrayList<Entry>(entries);
    }
    
    public void addEntry(Entry e) {
        entries.add(e);
    }
    
    public void addEntry(int index, Entry e) {
        entries.add(index, e);
    }
    
    public void removeEntry(Entry e) {
        entries.remove(e);
    }
    
    public void replaceEntry(Entry original, Entry changed) {
        int i = entries.indexOf(original);
        entries.remove(original);
        entries.add(i, changed);
    }
}
