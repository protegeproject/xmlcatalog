package org.protege.xmlcatalog.entry;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.XmlBaseContext;
import org.protege.xmlcatalog.XMLCatalog;

public abstract class AbstractDelegateEntry extends Entry implements XmlBaseContext {
    private URI catalog;
    private XMLCatalog delegate;
    private URI xmlbase;
    
    public AbstractDelegateEntry(String id, XmlBaseContext xmlBaseContext, URI catalog, URI xmlbase) {
        super(id, xmlBaseContext);
        this.catalog = catalog;
        this.xmlbase = xmlbase;
    }

    public URI getCatalog() {
        return catalog;
    }

    public void setCatalog(URI catalog) {
        this.catalog = catalog;
    }

    public XMLCatalog getParsedCatalog() throws IOException {
        if (delegate == null) {
            try {
                URI base = CatalogUtilities.resolveXmlBase(this);
                URI doc  = base.resolve(catalog);
                delegate = CatalogUtilities.parseDocument(doc.toURL());
            }
            catch (MalformedURLException mue) {
                IOException ioe = new IOException(mue.getMessage());
                ioe.initCause(mue);
                throw ioe;
            }
        }
        return delegate;
    }
    public URI getXmlBase() {
        return xmlbase;
    }

    public void setXmlBase(URI xmlbase) {
        this.xmlbase = xmlbase;
    }
    
    public String toString() {
    	return "--> Delegate Catalog(@" + delegate.getXmlBase() + ")";
    }
}
