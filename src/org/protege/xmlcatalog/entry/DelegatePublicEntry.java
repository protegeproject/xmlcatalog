package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;

public class DelegatePublicEntry extends DelegateEntry {
    private String publicIdStartString;

    public DelegatePublicEntry(String id, String publicIdStartString, URI catalog, URI xmlbase) {
        super(id, catalog, xmlbase);
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
}
