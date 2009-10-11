package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;

public class SystemEntry extends Entry {
    private String systemId;
    private URI uri;
    private URI xmlbase;
    
    public SystemEntry(String id, String systemId, URI uri, URI xmlbase) {
        super(id);
        this.systemId = systemId;
        this.uri = uri;
        this.xmlbase = xmlbase;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
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
