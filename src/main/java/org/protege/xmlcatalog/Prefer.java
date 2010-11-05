/**
 * 
 */
package org.protege.xmlcatalog;

import org.protege.xmlcatalog.parser.Handler;

public enum Prefer {
    PUBLIC(Handler.PREFER_PUBLIC_VALUE), SYSTEM(Handler.PREFER_SYSTEM_VALUE);
    
    private String name;
    
    private Prefer(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }
}