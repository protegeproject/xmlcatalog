package org.protege.xmlcatalog.write;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.entry.DelegateEntry;
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
import org.protege.xmlcatalog.parser.Handler;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


public class XMLRenderingVisitor implements EntryVisitor {
    private Document document;
    private Element root;
    private URI xmlbase;
    
    
    
    public XMLRenderingVisitor(Document document, Element root, URI xmlbase) {
        this.document = document;
        this.root = root;
        this.xmlbase = xmlbase;
    }

    public void visit(GroupEntry entry) {
        Element groupElement = document.createElement(Handler.GROUP_ELEMENT);
        if (entry.getPrefer() != null) {
            groupElement.setAttribute(Handler.PREFER_ATTRIBUTE, entry.getPrefer().getName());
        }
        if (entry.getXmlBase() != null) {
            groupElement.setAttribute(Handler.XML_BASE_ATTRIBUTE, entry.getXmlBase().toString());
        }
    }

    public void visit(PublicEntry entry) {
        // TODO Auto-generated method stub
        
    }

    public void visit(SystemEntry entry) {
        // TODO Auto-generated method stub
        
    }

    public void visit(RewriteSystemEntry entry) {
        // TODO Auto-generated method stub
        
    }

    public void visit(DelegatePublicEntry entry) {
        // TODO Auto-generated method stub
        
    }

    public void visit(DelegateSystemEntry entry) {
        // TODO Auto-generated method stub
        
    }

    public void visit(UriEntry entry) {
        // TODO Auto-generated method stub
        
    }

    public void visit(RewriteUriEntry entry) {
        // TODO Auto-generated method stub
        
    }

    public void visit(DelegateUriEntry entry) {
        // TODO Auto-generated method stub
        
    }

    public void visit(NextCatalogEntry entry) {
        // TODO Auto-generated method stub
        
    }
    
    private void addId(Element entryElement, Entry entry) {
        if (entry.getId() != null) {
            root.setAttribute(Handler.ID_ATTRIBUTE, entry.getId());
        }
    }
    
    private void addDelegate(Element entryElement, DelegateEntry delegateEntry) {
        if (delegateEntry.getXmlbase() != null) {
            entryElement.setAttribute(Handler.XML_BASE_ATTRIBUTE, delegateEntry.getXmlbase().toString());
        }
        entryElement.setAttribute(Handler.CATALOG_ATTRIBUTE, delegateEntry.getCatalog().toString());
    }

}
