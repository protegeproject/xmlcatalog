package org.protege.xmlcatalog;

import java.net.URI;

public interface XmlBaseContext {
    
    void setXmlBase(URI xmlbase);
    URI getXmlBase();
    XmlBaseContext getXmlBaseContext();

}
