package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public class UriEntry extends AbstractUriEntry {
    private String name;

    public UriEntry(String id, XmlBaseContext xmlBaseContext, String name, URI uri, URI xmlbase) {
        super(id, xmlBaseContext, uri, xmlbase);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }

}
