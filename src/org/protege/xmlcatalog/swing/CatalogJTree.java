package org.protege.xmlcatalog.swing;

import java.util.List;

import javax.swing.JTree;

import org.protege.xmlcatalog.XMLCatalog;

public class CatalogJTree extends JTree {
	private static final long serialVersionUID = -8502646155443727063L;

	public CatalogJTree(XMLCatalog catalog, List<EntryEditor>  editors) {
		super(new CatalogTreeNode(catalog, editors), true);
		setCellRenderer(new CellRenderer());
	}

}
