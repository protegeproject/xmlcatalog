package org.protege.xmlcatalog.parser;

import java.net.URI;
import java.util.Stack;

import org.protege.xmlcatalog.Prefer;
import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.XmlBaseContext;
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
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler extends DefaultHandler {
    /* *********************** Elements *********************** */
    public final static String CATALOG_ELEMENT           = "catalog";
    public final static String GROUP_ELEMENT             = "group";
    public final static String PUBLIC_ELEMENT            = "public";
    public final static String SYSTEM_ELEMENT            = "system";
    public final static String REWRITE_SYSTEM_ELEMENT    = "rewriteSystem";
    public final static String DELEGATE_PUBLIC_ELEMENT   = "delegatePublic";
    public final static String DELEGATE_SYSTEM_ELEMENT   = "delegateSystem";
    public final static String URI_ELEMENT               = "uri";
    public final static String REWRITE_URI_ELEMENT       = "rewriteURI";
    public final static String DELEGATE_URI_ELEMENT      = "delegateURI";
    public final static String NEXT_CATALOG_ELEMENT      = "nextCatalog";

    /* *********************** Attributes *********************** */
    public final static String ID_ATTRIBUTE              = "id";
    public final static String PREFER_ATTRIBUTE          = "prefer";
    public final static String XML_BASE_ATTRIBUTE        = "xml:base";

    /* public element */
    public final static String PUBLIC_ID_ATTRIBUTE       = "publicId";
    public final static String URI_ATTRIBUTE             = "uri";

    /* system element */
    public final static String SYSTEM_ID_ATTRIBUTE       = "systemId";

    /* rewriteSystem element */
    public final static String SYSTEM_ID_START_ATTRIBUTE = "systemIdStartString";
    public final static String REWRITE_PREFIX_ATTRIBUTE  = "rewritePrefix";

    /* delegatePublic element */
    public final static String PUBLIC_ID_START_ATTRIBUTE = "publicIdStartString";

    /* uri element */
    public final static String URI_NAME_ATTRIBUTE        = "name";

    /* rewriteURI element */
    public final static String URI_START_STRING          = "uriStartString";

    /* nextCatalog element */
    public final static String CATALOG_ATTRIBUTE         = "catalog";

    /* *********************** Values *********************** */
    /* public element */
    public final static String PREFER_PUBLIC_VALUE       = "public";
    public final static String PREFER_SYSTEM_VALUE       = "system";

    private XMLCatalog catalog;
    private XmlBaseContext outerContext;
    private Stack<GroupEntry> groupStack = new Stack<GroupEntry>();
    
    public Handler() {
        
    }
    
    public Handler(URI xmlbase) {
        this(new OuterXmlBaseContext(xmlbase));
    }
    
    public Handler(XmlBaseContext context) {
        outerContext = context;
    }
    
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals(CATALOG_ELEMENT)) {
            catalog = new XMLCatalog(getId(attributes), outerContext, getPrefer(attributes), getXmlBase(attributes));
        }
        else if (qName.equals(GROUP_ELEMENT)) {
            GroupEntry group = new GroupEntry(getId(attributes), getXmlBaseContext(), getPrefer(attributes),  getXmlBase(attributes));
            addEntry(group);
            groupStack.push(group);
        }
        else if (qName.equals(PUBLIC_ELEMENT)) {
            addEntry(new PublicEntry(getId(attributes), 
                                     getXmlBaseContext(),
                                     attributes.getValue(PUBLIC_ID_ATTRIBUTE), 
                                     URI.create(attributes.getValue(URI_ATTRIBUTE)),
                                     getXmlBase(attributes)));
        }
        else if (qName.equals(SYSTEM_ELEMENT)) {
            addEntry(new SystemEntry(getId(attributes), 
                                     getXmlBaseContext(),
                                     attributes.getValue(SYSTEM_ID_ATTRIBUTE), 
                                     URI.create(attributes.getValue(URI_ATTRIBUTE)),
                                     getXmlBase(attributes)));
        }
        else if (qName.equals(REWRITE_SYSTEM_ELEMENT)) {
            addEntry(new RewriteSystemEntry(getId(attributes), 
                                            getXmlBaseContext(),
                                            attributes.getValue(SYSTEM_ID_START_ATTRIBUTE), 
                                            URI.create(attributes.getValue(REWRITE_PREFIX_ATTRIBUTE))));
        }
        else  if (qName.equals(DELEGATE_PUBLIC_ELEMENT)) {
            addEntry(new DelegatePublicEntry(getId(attributes),
                                             getXmlBaseContext(),
                                             attributes.getValue(PUBLIC_ID_START_ATTRIBUTE),
                                             URI.create(attributes.getValue(CATALOG_ATTRIBUTE)),
                                             getXmlBase(attributes)));
        }
        else  if (qName.equals(DELEGATE_SYSTEM_ELEMENT)) {
            addEntry(new DelegateSystemEntry(getId(attributes),
                                             getXmlBaseContext(),
                                             attributes.getValue(SYSTEM_ID_START_ATTRIBUTE),
                                             URI.create(attributes.getValue(CATALOG_ATTRIBUTE)),
                                             getXmlBase(attributes)));
        }
        else  if (qName.equals(URI_ELEMENT)) {
            addEntry(new UriEntry(getId(attributes),
                                  getXmlBaseContext(),
                                  attributes.getValue(URI_NAME_ATTRIBUTE),
                                  URI.create(attributes.getValue(URI_ATTRIBUTE)),
                                  getXmlBase(attributes)));
        }
        else  if (qName.equals(REWRITE_URI_ELEMENT)) {
            addEntry(new RewriteUriEntry(getId(attributes),
                                         getXmlBaseContext(),
                                         attributes.getValue(URI_START_STRING),
                                         URI.create(attributes.getValue(REWRITE_PREFIX_ATTRIBUTE))));
        }
        else  if (qName.equals(DELEGATE_URI_ELEMENT)) {
            addEntry(new DelegateUriEntry(getId(attributes),
                                          getXmlBaseContext(),
                                          attributes.getValue(URI_START_STRING),
                                          URI.create(attributes.getValue(CATALOG_ATTRIBUTE)),
                                          getXmlBase(attributes)));
        }
        else if (qName.equals(NEXT_CATALOG_ELEMENT)) {
            addEntry(new NextCatalogEntry(getId(attributes), 
                                          getXmlBaseContext(),
                                          URI.create(attributes.getValue(CATALOG_ATTRIBUTE)), 
                                          getXmlBase(attributes)));
        }
        else {
            throw new UnsupportedOperationException("not implemented yet");
        }
    }
    
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals(GROUP_ELEMENT)) {
            groupStack.pop();
        }
    }

    public XMLCatalog getCatalog() {
        return catalog;
    }
    
    private void addEntry(Entry entry) {
        if (groupStack.isEmpty()) {
            catalog.addEntry(entry);
        }
        else {
            groupStack.peek().addEntry(entry);
        }
    }
    
    private String getId(Attributes attributes) {
        return attributes.getValue(ID_ATTRIBUTE);
    }
    
    private Prefer getPrefer() {
        if (groupStack.isEmpty()) {
            return catalog.getPrefer();
        }
        else {
            return groupStack.peek().getPrefer();
        }
    }
    
    private Prefer getPrefer(Attributes attributes) {
        String preferString = attributes.getValue(PREFER_ATTRIBUTE);
        if (preferString == null) {
            return getPrefer();
        }
        else if (preferString.toLowerCase().equals(PREFER_PUBLIC_VALUE)) {
            return Prefer.PUBLIC;
        }
        else if (preferString.toLowerCase().equals(PREFER_SYSTEM_VALUE)) {
            return Prefer.SYSTEM;
        }
        else  {
            return null;
        }
    }

    
    private URI getXmlBase(Attributes attributes) {
        String baseString = attributes.getValue(XML_BASE_ATTRIBUTE);
        if (baseString != null) {
            return URI.create(baseString);
        }
        else {
            return null;
        }
    }
    
    private XmlBaseContext getXmlBaseContext() {
        if (groupStack.isEmpty()) {
            return catalog;
        }
        else {
            return groupStack.peek();
        }
    }
}
