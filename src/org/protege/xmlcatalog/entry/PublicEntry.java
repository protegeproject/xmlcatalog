package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public class PublicEntry extends AbstractUriEntry {
    private String publicId;
    
    public PublicEntry(String id, XmlBaseContext xmlBaseContext, String publicId, URI uri, URI xmlbase) {
        super(id, xmlBaseContext, uri, xmlbase);
        this.publicId = publicId;
    }
    
    public String getPublicId() {
        return publicId;
    }
    
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }
}
