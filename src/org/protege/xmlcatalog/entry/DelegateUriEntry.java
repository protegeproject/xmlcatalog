package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;

public class DelegateUriEntry extends DelegateEntry {
    private String uriStartString;
    
    public DelegateUriEntry(String id, String uriStartString, URI catalog, URI xmlbase) {
        super(id, catalog, xmlbase);
        this.uriStartString = uriStartString;
    }

    public String getUriStartString() {
        return uriStartString;
    }

    public void setUriStartString(String uriStartString) {
        this.uriStartString = uriStartString;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }

}
