package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public class NextCatalogEntry extends AbstractDelegateEntry {
    
    public NextCatalogEntry(String id, XmlBaseContext xmlBaseContext, URI catalog, URI xmlbase) {
        super(id, xmlBaseContext, catalog, xmlbase);
    }
    
    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }

}
