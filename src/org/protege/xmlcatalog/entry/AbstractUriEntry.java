package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.Util;
import org.protege.xmlcatalog.XmlBaseContext;

public abstract class AbstractUriEntry extends Entry implements XmlBaseContext {
    private URI uri;
    private URI xmlBase;
    
    protected AbstractUriEntry(String id, XmlBaseContext context, URI uri, URI xmlBase) {
        super(id, context);
        this.uri = uri;
        this.xmlBase = xmlBase;
    }

    public URI getUri() {
        return uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }
    
    public URI getXmlBase() {
        return xmlBase;
    }

    public void setXmlBase(URI xmlbase) {
        this.xmlBase = xmlbase;
    }
    
    public URI getAbsoluteURI() {
        return Util.resolveUri(getUri(), this);
    }
}
