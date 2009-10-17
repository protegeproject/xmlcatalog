package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public class DelegateUriEntry extends AbstractDelegateEntry {
    private String uriStartString;
    
    public DelegateUriEntry(String id, XmlBaseContext xmlBaseContext, String uriStartString, URI catalog, URI xmlbase) {
        super(id, xmlBaseContext, catalog, xmlbase);
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
