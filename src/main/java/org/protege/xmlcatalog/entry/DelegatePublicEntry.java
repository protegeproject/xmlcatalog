package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public class DelegatePublicEntry extends AbstractDelegateEntry {
    private String publicIdStartString;

    public DelegatePublicEntry(String id, XmlBaseContext xmlBaseContext, String publicIdStartString, URI catalog, URI xmlbase) {
        super(id, xmlBaseContext, catalog, xmlbase);
        this.publicIdStartString = publicIdStartString;
    }

    public String getPublicIdStartString() {
        return publicIdStartString;
    }

    public void setPublicIdStartString(String publicIdStartString) {
        this.publicIdStartString = publicIdStartString;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }
    
    @Override
    public String toString() {
    	return "Public " + publicIdStartString + super.toString();
    }
}
