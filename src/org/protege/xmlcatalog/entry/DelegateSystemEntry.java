package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;

public class DelegateSystemEntry extends DelegateEntry {
    private String systemIdStartString;
    
    public DelegateSystemEntry(String id, String systemIdStartString, URI catalog, URI xmlbase) {
        super(id, catalog, xmlbase);
        this.systemIdStartString = systemIdStartString;
    }

    public String getSystemIdStartString() {
        return systemIdStartString;
    }

    public void setSystemIdStartString(String systemIdStartString) {
        this.systemIdStartString = systemIdStartString;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }

}
