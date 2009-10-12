package org.protege.xmlcatalog;

import org.protege.xmlcatalog.entry.DelegatePublicEntry;
import org.protege.xmlcatalog.entry.DelegateSystemEntry;
import org.protege.xmlcatalog.entry.DelegateUriEntry;
import org.protege.xmlcatalog.entry.GroupEntry;
import org.protege.xmlcatalog.entry.NextCatalogEntry;
import org.protege.xmlcatalog.entry.PublicEntry;
import org.protege.xmlcatalog.entry.RewriteSystemEntry;
import org.protege.xmlcatalog.entry.RewriteUriEntry;
import org.protege.xmlcatalog.entry.SystemEntry;
import org.protege.xmlcatalog.entry.UriEntry;

public interface EntryVisitor {
    void visit(GroupEntry entry);
    void visit(PublicEntry entry);
    void visit(SystemEntry entry);
    void visit(RewriteSystemEntry entry);
    void visit(DelegatePublicEntry entry);
    void visit(DelegateSystemEntry entry);
    void visit(UriEntry entry);
    void visit(RewriteUriEntry entry);
    void visit(DelegateUriEntry entry);
    void visit(NextCatalogEntry entry);
}
