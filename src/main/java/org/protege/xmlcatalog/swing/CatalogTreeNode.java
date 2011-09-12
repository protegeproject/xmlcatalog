package org.protege.xmlcatalog.swing;

import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.tree.TreeNode;

import org.protege.xmlcatalog.XMLCatalog;
import org.protege.xmlcatalog.entry.Entry;
import org.protege.xmlcatalog.entry.NextCatalogEntry;

public class CatalogTreeNode implements TreeNode {
	private TreeNode parent;
	private XMLCatalog catalog;
	private List<EntryEditor> editors;
	
	public CatalogTreeNode(XMLCatalog catalog, List<EntryEditor> editors) {
		this(null, catalog, editors);
	}	
	
	public CatalogTreeNode(TreeNode parent, XMLCatalog catalog, List<EntryEditor> editors) {
		this.parent = parent;
		this.catalog = catalog;
		this.editors = editors;
	}

	public XMLCatalog getCatalog() {
		return catalog;
	}
	
	public List<EntryEditor> getEditors() {
		return editors;
	}
	
	/* ************************************************
	 * TreeNode interfaces.
	 */
	
	
	public TreeNode getChildAt(int childIndex) {
		Entry e = catalog.getEntries().get(childIndex);
		return new EntryTreeNode(this, e, editors);
	}

	
	public int getChildCount() {
		return catalog.getEntries().size();
	}

	
	public TreeNode getParent() {
		return parent;
	}

	
	public int getIndex(TreeNode node) {
		if (parent instanceof EntryTreeNode &&
				((EntryTreeNode)  parent).getEditor() instanceof NextCatalogEntry &&
				parent.getChildAt(0) == catalog) {
			return 0;
		}
		return -1;
	}

	
	public boolean getAllowsChildren() {
		return true;
	}

	
	public boolean isLeaf() {
		return catalog.getEntries().size() == 0;
	}

	
	public Enumeration<TreeNode> children() {
		return new Enumeration<TreeNode>() {
			int currentIndex = 0;
			
			public boolean hasMoreElements() {
				return currentIndex < getChildCount();
			}
			
			public TreeNode nextElement() throws NoSuchElementException {
				if (!hasMoreElements()) {
					throw new NoSuchElementException("No such element");
				}
				try {
					return getChildAt(currentIndex);
				}
				finally {
					currentIndex++;
				}
			}
		};
	}

}
