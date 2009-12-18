package org.protege.xmlcatalog;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
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
    	this(xmlBase.toString(), new OuterXmlBaseContext(xmlBase), Prefer.PUBLIC, xmlBase);
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
        return Collections.unmodifiableList(entries);
    }
    
    public void addEntry(Entry e) {
        entries.add(e);
    }
    
    public void removeEntry(Entry e) {
        entries.remove(e);
    }
}
