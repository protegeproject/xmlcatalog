package org.protege.xmlcatalog.write;

import java.net.URI;

import org.protege.xmlcatalog.EntryVisitor;
import org.protege.xmlcatalog.entry.AbstractDelegateEntry;
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
        addId(groupElement, entry);
        if (entry.getPrefer() != null) {
            groupElement.setAttribute(Handler.PREFER_ATTRIBUTE, entry.getPrefer().getName());
        }
        if (entry.getXmlBase() != null) {
            groupElement.setAttribute(Handler.XML_BASE_ATTRIBUTE, entry.getXmlBase().toString());
        }
        root.appendChild(groupElement);
    }

    public void visit(PublicEntry entry) {
        Element publicElement = document.createElement(Handler.PUBLIC_ELEMENT);
        addId(publicElement, entry);
        publicElement.setAttribute(Handler.PUBLIC_ID_ATTRIBUTE, entry.getPublicId());
        publicElement.setAttribute(Handler.URI_ATTRIBUTE, entry.getUri().toString());
        if (entry.getXmlBase() != null) {
            publicElement.setAttribute(Handler.XML_BASE_ATTRIBUTE, entry.getXmlBase().toString());
        }
        root.appendChild(publicElement);
    }

    public void visit(SystemEntry entry) {
        Element systemElement = document.createElement(Handler.SYSTEM_ELEMENT);
        addId(systemElement, entry);
        systemElement.setAttribute(Handler.SYSTEM_ID_ATTRIBUTE, entry.getSystemId());
        systemElement.setAttribute(Handler.URI_ATTRIBUTE, entry.getUri().toString());
        if (entry.getXmlBase() != null) {
            systemElement.setAttribute(Handler.XML_BASE_ATTRIBUTE, entry.getXmlBase().toString());
        }
        root.appendChild(systemElement); 
    }

    public void visit(RewriteSystemEntry entry) {
        Element rewriteSystemElement = document.createElement(Handler.REWRITE_SYSTEM_ELEMENT);
        addId(rewriteSystemElement, entry);
        rewriteSystemElement.setAttribute(Handler.SYSTEM_ID_START_ATTRIBUTE, entry.getSystemIdStartString());
        rewriteSystemElement.setAttribute(Handler.REWRITE_PREFIX_ATTRIBUTE, entry.getRewritePrefix().toString());
        root.appendChild(rewriteSystemElement); 
    }

    public void visit(DelegatePublicEntry entry) {
        Element delgatePublicElement = document.createElement(Handler.DELEGATE_PUBLIC_ELEMENT);
        addId(delgatePublicElement, entry);
        addDelegate(delgatePublicElement, entry);
        delgatePublicElement.setAttribute(Handler.PUBLIC_ID_START_ATTRIBUTE, entry.getPublicIdStartString());
        root.appendChild(delgatePublicElement);
    }

    public void visit(DelegateSystemEntry entry) {
        Element delegateSystemElement = document.createElement(Handler.DELEGATE_SYSTEM_ELEMENT);
        addId(delegateSystemElement, entry);
        addDelegate(delegateSystemElement, entry);
        delegateSystemElement.setAttribute(Handler.SYSTEM_ID_START_ATTRIBUTE, entry.getSystemIdStartString());
        root.appendChild(delegateSystemElement);
    }

    public void visit(UriEntry entry) {
        Element uriElement = document.createElement(Handler.URI_ELEMENT);
        addId(uriElement, entry);
        uriElement.setAttribute(Handler.URI_NAME_ATTRIBUTE, entry.getName());
        uriElement.setAttribute(Handler.URI_ELEMENT, entry.getUri().toString());
        if (entry.getXmlBase() != null) {
            uriElement.setAttribute(Handler.XML_BASE_ATTRIBUTE, entry.getXmlBase().toString());
        }
        root.appendChild(uriElement);
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
    
    private void addDelegate(Element entryElement, AbstractDelegateEntry delegateEntry) {
        if (delegateEntry.getXmlBase() != null) {
            entryElement.setAttribute(Handler.XML_BASE_ATTRIBUTE, delegateEntry.getXmlBase().toString());
        }
        entryElement.setAttribute(Handler.CATALOG_ATTRIBUTE, delegateEntry.getCatalog().toString());
    }

}
