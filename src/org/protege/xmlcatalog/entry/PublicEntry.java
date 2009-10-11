package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;

public class PublicEntry extends Entry {
    private String publicId;
    private URI uri;
    private URI xmlbase;
    
    public PublicEntry(String id, String publicId, URI uri, URI xmlbase) {
        super(id);
        this.publicId = publicId;
        this.uri = uri;
        this.xmlbase = xmlbase;
    }
    
    public String getPublicId() {
        return publicId;
    }
    
    public void setPublicId(String publicId) {
        this.publicId = publicId;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public URI getXmlbase() {
        return xmlbase;
    }

    public void setXmlbase(URI xmlbase) {
        this.xmlbase = xmlbase;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }
}
