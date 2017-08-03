package org.protege.xmlcatalog.redirect;

import java.io.IOException;
import java.net.URI;

import org.apache.log4j.Logger;
import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.entry.*;

public class UriRedirectVisitor implements EntryVisitor {
    private static Logger log = Logger.getLogger(UriRedirectVisitor.class);
    private URI original;
    private URI redirect;
    private URI rewritedUri;
    private int rewriteBestSize = 0;
    
    public UriRedirectVisitor(URI original) {
        this.original = original;
    }
    
    public URI getRedirect() {
        return rewritedUri == null ? redirect : rewritedUri;
    }

    public void visit(GroupEntry entry) {
        for (Entry subEntry : entry.getEntries()) {
            subEntry.accept(this);
        }
    }

    public void visit(PublicEntry entry) {
        ;
    }

    public void visit(SystemEntry entry) {
        ;
    }

    public void visit(RewriteSystemEntry entry) {
        ;
    }

    public void visit(DelegatePublicEntry entry) {
        ;
    }

    public void visit(DelegateSystemEntry entry) {
        ;
    }

    public void visit(UriEntry entry) {
        if (original.equals(URI.create(entry.getName()))) {
            redirect = entry.getAbsoluteURI();
        }
    }

    public void visit(RewriteUriEntry entry) {
        try {
            String redirectedUri = redirect == null ? null : redirect.toString();
            if(redirectedUri == null){
                return;
            }
            if (redirectedUri.startsWith(entry.getUriStartString()) && entry.getUriStartString().length() > rewriteBestSize) {
                String suffix = redirectedUri.substring(entry.getUriStartString().length());
                rewritedUri = URI.create(entry.getRewritePrefix().toString() + suffix);
                if(!rewritedUri.isAbsolute()){
                    rewritedUri = AbstractUriEntry.resolveUriAgainstXmlBase(rewritedUri, entry.getXmlBaseContext());
                }
                rewriteBestSize = entry.getUriStartString().length();
            }
        }
        catch (Throwable e) {
            log.error("Exception caught trying to resolve " + original,  e);
        }
    }

    public void visit(DelegateUriEntry entry) {
        try {
            if (original.isAbsolute()) {
                String originalString = original.toString();
                if (originalString.startsWith(entry.getUriStartString())) {
                    visitCatalog(entry.getParsedCatalog());
                }
            }
        }
        catch (IOException ioe) {
            log.error("Exception caught trying to resolve " + original, ioe);
        }
    }

    public void visit(NextCatalogEntry entry) {
        try {
            visitCatalog(entry.getParsedCatalog());
        }
        catch (IOException ioe) {
            log.error("Exception caught trying to resolve " + original,  ioe);
        }
    }
    
    private void visitCatalog(XMLCatalog catalog) {
        for (Entry subEntry : catalog.getEntries()) {
            subEntry.accept(this);
        }
    }

}
