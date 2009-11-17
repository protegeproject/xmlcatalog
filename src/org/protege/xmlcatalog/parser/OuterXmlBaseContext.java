package org.protege.xmlcatalog.parser;

import java.net.URI;

import org.protege.xmlcatalog.XmlBaseContext;

public class OuterXmlBaseContext implements XmlBaseContext {
    private URI xmlbase;
    
    public OuterXmlBaseContext(URI xmlbase) {
        this.xmlbase = xmlbase;
    }

    public URI getXmlBase() {
        return xmlbase;
    }

    public XmlBaseContext getXmlBaseContext() {
        return null;
    }

    public void setXmlBase(URI xmlbase) {
        this.xmlbase = xmlbase;
    }

}
