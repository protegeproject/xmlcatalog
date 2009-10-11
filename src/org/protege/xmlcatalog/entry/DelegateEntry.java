package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.XMLCatalog;

public abstract class DelegateEntry extends Entry {
    private URI catalog;
    private XMLCatalog delegate;
    private URI xmlbase;
    
    public DelegateEntry(String id, URI catalog, URI xmlbase) {
        super(id);
        this.catalog = catalog;
        this.xmlbase = xmlbase;
    }

    public URI getCatalog() {
        return catalog;
    }

    public void setCatalog(URI catalog) {
        this.catalog = catalog;
    }

    public XMLCatalog getParsedCatalog() {
        if (delegate == null) {
            throw new UnsupportedOperationException("not implemented yet");
        }
        return delegate;
    }
    public URI getXmlbase() {
        return xmlbase;
    }

    public void setXmlbase(URI xmlbase) {
        this.xmlbase = xmlbase;
    }
}
