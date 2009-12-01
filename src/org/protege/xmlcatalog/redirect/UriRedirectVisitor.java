package org.protege.xmlcatalog.redirect;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.log4j.Logger;
import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.entry.AbstractUriEntry;
import org.protege.xmlcatalog.entry.DelegatePublicEntry;
import org.protege.xmlcatalog.entry.DelegateSystemEntry;
import org.protege.xmlcatalog.entry.DelegateUriEntry;
import org.protege.xmlcatalog.entry.Entry;
import org.protege.xmlcatalog.entry.GroupEntry;
import org.protege.xmlcatalog.entry.NextCatalogEntry;
import org.protege.xmlcatalog.entry.PublicEntry;
import org.protege.xmlcatalog.entry.RewriteSystemEntry;
import org.protege.xmlcatalog.entry.RewriteUriEntry;
import org.protege.xmlcatalog.entry.SystemEntry;
import org.protege.xmlcatalog.entry.UriEntry;

public class UriRedirectVisitor implements EntryVisitor {
    private static Logger log = Logger.getLogger(UriRedirectVisitor.class);
    private URI original;
    private URI redirect;
    
    public UriRedirectVisitor(URI original) {
        this.original = original;
    }
    
    public URI getRedirect() {
        return redirect;
    }

    public void visit(GroupEntry entry) {
        for (Entry subEntry : entry.getEntries()) {
            subEntry.accept(this);
            if (redirect != null) {
                break;
            }
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
        if (original.isAbsolute()) {
            try {
                URI relative = original.relativize(new URI(entry.getUriStartString()));
                if (!relative.isAbsolute()) {
                    redirect = AbstractUriEntry.resolveUriAgainstXmlBase(entry.getRewritePrefix(), entry.getXmlBaseContext()).resolve(relative);
                }
            }
            catch (URISyntaxException e) {
                log.error("Exception caught trying to resolve " + original,  e);
            }
        }
    }

    public void visit(DelegateUriEntry entry) {
        if (original.isAbsolute()) {
            try {
                URI relative = original.relativize(new URI(entry.getUriStartString()));
                if (!relative.isAbsolute()) {
                    visitCatalog(entry.getParsedCatalog());
                }
            }
            catch (URISyntaxException e) {
                log.error("Exception caught trying to resolve " + original,  e);
            }
            catch (IOException ioe) {
                log.error("Exception caught trying to resolve " + original,  ioe);
            }
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
            if (redirect != null) {
                break;
            }
        }
    }

}
