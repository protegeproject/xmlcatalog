package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;

public class NextCatalogEntry extends DelegateEntry {
    
    public NextCatalogEntry(String id, URI catalog, URI xmlbase) {
        super(id, catalog, xmlbase);
    }
    
    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }

}
