package org.protege.xmlcatalog;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.protege.xmlcatalog.entry.Entry;

public class XMLCatalog {
    public enum Prefer {
        PUBLIC, SYSTEM;
    }
    private String id;
    private Prefer prefer;
    private URI xmlBase;
    private List<Entry> entries = new ArrayList<Entry>();
    
    public XMLCatalog(String id, Prefer prefer, URI xmlBase) {
        this.id = id;
        this.prefer = prefer;
        this.xmlBase = xmlBase;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
