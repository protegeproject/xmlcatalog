package org.protege.xmlcatalog.entry;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public abstract class Entry {
    private String id;
    private XmlBaseContext xmlBaseContext;

    protected Entry(String id, XmlBaseContext xmlBaseContext) {
        super();
        this.id = id;
        this.xmlBaseContext = xmlBaseContext;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    public XmlBaseContext getXmlBaseContext() {
        return xmlBaseContext;
    }

    public abstract void accept(EntryVisitor visitor);
}
