package org.protege.xmlcatalog.swing;

import java.awt.Frame;

import org.protege.xmlcatalog.XmlBaseContext;
import org.protege.xmlcatalog.entry.Entry;

public interface EntryEditor {
	boolean isSuitable(Entry ge);
	
	Entry newEntryDialog(Frame parent, XmlBaseContext context);
    
	Entry editEntryDialog(Frame parent, XmlBaseContext context, Entry input);
    
	String getDescription(Entry ge);
}
