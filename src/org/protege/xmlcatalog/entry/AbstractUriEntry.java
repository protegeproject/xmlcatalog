package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.CatalogUtilities;
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
        return AbstractUriEntry.resolveUriAgainstXmlBase(getUri(), this);
    }

    public static URI resolveUriAgainstXmlBase(URI relative, XmlBaseContext context) {
        URI xmlbase = CatalogUtilities.resolveXmlBase(context);
        if (xmlbase == null) {
            return relative;
        }
        else {
            return xmlbase.resolve(relative);
        }
    }
    
    @Override
    public String toString() {
    	return "--> " + getUri();
    }
    
}
