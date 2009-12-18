package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public class DelegateSystemEntry extends AbstractDelegateEntry {
    private String systemIdStartString;
    
    public DelegateSystemEntry(String id, XmlBaseContext xmlBaseContext, String systemIdStartString, URI catalog, URI xmlbase) {
        super(id, xmlBaseContext, catalog, xmlbase);
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
    
    @Override
    public String toString() {
    	return "System " + systemIdStartString + super.toString();
    }
}
