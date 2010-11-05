package org.protege.xmlcatalog.entry;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XmlBaseContext;

public class RewriteUriEntry extends Entry {
    private String uriStartString;
    private URI rewritePrefix;

    public RewriteUriEntry(String id, XmlBaseContext xmlBaseContext, String uriStartString, URI rewritePrefix) {
        super(id, xmlBaseContext);
        this.uriStartString = uriStartString;
        this.rewritePrefix = rewritePrefix;
    }
    
    public String getUriStartString() {
        return uriStartString;
    }

    public void setUriStartString(String uriStartString) {
        this.uriStartString = uriStartString;
    }

    public URI getRewritePrefix() {
        return rewritePrefix;
    }

    public void setRewritePrefix(URI rewritePrefix) {
        this.rewritePrefix = rewritePrefix;
    }

    @Override
    public void accept(EntryVisitor visitor) {
        visitor.visit(this);
    }

}
