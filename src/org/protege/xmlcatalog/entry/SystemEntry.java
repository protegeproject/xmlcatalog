package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public class SystemEntry extends AbstractUriEntry {
    private String systemId;
    
    public SystemEntry(String id, XmlBaseContext xmlBaseContext, String systemId, URI uri, URI xmlbase) {
        super(id, xmlBaseContext, uri, xmlbase);
        this.systemId = systemId;
    }

    public String getSystemId() {
        return systemId;
    }

    public void setSystemId(String systemId) {
        this.systemId = systemId;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }

}
