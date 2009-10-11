package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;

public class UriEntry extends Entry {
    private String name;
    private URI uri;
    private URI xmlbase;

    public UriEntry(String id, String name, URI uri, URI xmlbase) {
        super(id);
        this.name = name;
        this.uri = uri;
        this.xmlbase = xmlbase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
