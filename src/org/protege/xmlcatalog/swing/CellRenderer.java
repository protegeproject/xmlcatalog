package org.protege.xmlcatalog.swing;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.TreeCellRenderer;

import org.protege.xmlcatalog.EntryVisitor;
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

import sun.swing.DefaultLookup;

public class CellRenderer implements TreeCellRenderer {
	private JLabel label = new JLabel();
	private EntryRenderingVisitor visitor = new EntryRenderingVisitor();
	private boolean colorsInitialized = false;

    // Colors
    /** Color to use for the foreground for selected nodes. */
    protected Color textSelectionColor;

    /** Color to use for the foreground for non-selected nodes. */
    protected Color textNonSelectionColor;

    /** Color to use for the background when a node is selected. */
    protected Color backgroundSelectionColor;

    /** Color to use for the background when the node isn't selected. */
    protected Color backgroundNonSelectionColor;
	
	public Component getTreeCellRendererComponent(JTree tree, Object value,
												  boolean selected, boolean expanded, boolean leaf, int row,
												  boolean hasFocus) {

		if (selected) {
			label.setBackground(tree.getForeground());
		}
		else {
			label.setBackground(tree.getBackground());
		}
		if (value instanceof CatalogTreeNode) {
			label.setText(((CatalogTreeNode) value).getCatalog().getId());
		}
		else {
			EntryTreeNode node = (EntryTreeNode) value;
			if (node.getEditor() != null) {
				label.setText(node.getEditor().getDescription(node.getEntry()));
			}
			else {
				node.getEntry().accept(visitor);
			}
		}
		return label;
	}
	
	private class EntryRenderingVisitor implements EntryVisitor  {

		
		public void visit(GroupEntry entry) {
			label.setText("Group Entry");
		}

		
		public void visit(PublicEntry entry) {
			label.setText("Not implemented yet");
		}

		
		public void visit(SystemEntry entry) {
			label.setText("Not implemented yet");
		}

		
		public void visit(RewriteSystemEntry entry) {
			label.setText("Not implemented yet");
		}

		
		public void visit(DelegatePublicEntry entry) {
			label.setText("Not implemented yet");
		}

		
		public void visit(DelegateSystemEntry entry) {
			label.setText("Not implemented yet");
		}

		
		public void visit(UriEntry entry) {
			label.setText(entry.getName() + " --> " + entry.getUri());
		}

		
		public void visit(RewriteUriEntry entry) {
			label.setText("Not implemented yet");
		}

		
		public void visit(DelegateUriEntry entry) {
			label.setText("Not implemented yet");
		}

		
		public void visit(NextCatalogEntry entry) {
			label.setText("Not implemented yet");
		}
		
	}

}
