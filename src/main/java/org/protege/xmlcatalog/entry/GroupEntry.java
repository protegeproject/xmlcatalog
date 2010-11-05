package org.protege.xmlcatalog.entry;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.Prefer;
import org.protege.xmlcatalog.XmlBaseContext;

public class GroupEntry extends Entry implements XmlBaseContext {
    private Prefer prefer;
    private URI xmlBase;
    private List<Entry> entries = new ArrayList<Entry>();

    public GroupEntry(String id, XmlBaseContext xmlBaseContext, Prefer prefer, URI xmlBase) {
        super(id, xmlBaseContext);
        this.prefer = prefer;
        this.xmlBase = xmlBase;
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

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
    	return "Group Entry(" + getId() + ") with " + entries.size() + " entries";
    }

}
