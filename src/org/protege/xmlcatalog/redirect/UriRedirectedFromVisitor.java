package org.protege.xmlcatalog.redirect;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;
import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.XMLCatalog;
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

public class UriRedirectedFromVisitor implements EntryVisitor {
    private static Logger log = Logger.getLogger(UriRedirectedFromVisitor.class);
    private Set<URI> originals = new HashSet<URI>();
    private URI redirect;
    
    public UriRedirectedFromVisitor(URI redirect) {
        this.redirect = redirect;
    }
    
    public Set<URI> getOriginals() {
        return originals;
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
        if (redirect.equals(entry.getAbsoluteURI())) {
            try {
                originals.add(new URI(entry.getName()));
            }
            catch (URISyntaxException uris) {
                if (log.isDebugEnabled()) {
                    log.debug(entry.toString() + " could not construct original URI from redirect " + redirect);
                }
            }
        }
    }

    public void visit(RewriteUriEntry entry) {
        URI xmlbase = CatalogUtilities.resolveXmlBase(entry.getXmlBaseContext());
        if (xmlbase != null) {
            URI relative = xmlbase.relativize(redirect);
            if (!relative.isAbsolute()) {
                try {
                    originals.add(new URI(entry.getUriStartString()).resolve(relative.toString()));
                }
                catch (URISyntaxException urie) {
                    if (log.isDebugEnabled()) {
                        log.debug(entry.toString() + " could not construct original URI from redirect " + redirect);
                    }
                }
            }
        }
    }

    public void visit(DelegateUriEntry entry) {
        UriRedirectedFromVisitor subVisitor = new UriRedirectedFromVisitor(redirect);
        try {
            visitCatalog(subVisitor, entry.getParsedCatalog());
        }
        catch (IOException  ioe) {
            log.error("Malformed delegate entry " + entry.getCatalog());
            return;
        }
        for (URI original : subVisitor.getOriginals()) {
            URI relative;
            try {
                relative = original.relativize(new URI(entry.getUriStartString()));
            }
            catch (URISyntaxException e) {
                if (log.isDebugEnabled()) {
                    log.debug(entry.toString() + " could not construct original URI from redirect " + redirect);
                }
                continue;
            }
            if (!relative.isAbsolute()) {
                originals.add(original);
            }
        }
    }

    public void visit(NextCatalogEntry entry) {
        try {
            visitCatalog(this, entry.getParsedCatalog());
        }
        catch (IOException ioe) {
            log.error("Next catalog entry malformed " + entry.getCatalog());
        }
    }
    
    private void visitCatalog(EntryVisitor visitor, XMLCatalog catalog) {
        for (Entry subEntry : catalog.getEntries()) {
            subEntry.accept(visitor);
        }
    }

}
