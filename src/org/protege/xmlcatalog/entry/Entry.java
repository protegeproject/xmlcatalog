package org.protege.xmlcatalog.entry;

import org.protege.xmlcatalog.EntryVisitor;

public abstract class Entry {
    private String id;

    protected Entry(String id) {
        super();
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract void accept(EntryVisitor visitor);
}
