package org.protege.xmlcatalog.swing;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

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

public class AltCellRenderer extends DefaultTreeCellRenderer {
	private EntryRenderingVisitor visitor = new EntryRenderingVisitor();

	public Component getTreeCellRendererComponent(JTree tree, Object value,
												  boolean selected, boolean expanded, boolean leaf, int row,
												  boolean hasFocus) {
		JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		if (value instanceof CatalogTreeNode) {
			label.setText(((CatalogTreeNode) value).getCatalog().getId());
		} else {
			EntryTreeNode node = (EntryTreeNode) value;
			if (node.getEditor() != null) {
				label.setText(node.getEditor().getDescription(node.getEntry()));
			} else {
				node.getEntry().accept(visitor);
				label.setText(visitor.getText());
			}
		}
		if (selected) {
			label.setBackground(backgroundSelectionColor);
		} else {
			label.setBackground(backgroundNonSelectionColor);
		}
		return label;
	}

	private class EntryRenderingVisitor implements EntryVisitor {
		private String text;

		public String getText() {
			return text;
		}
		
		public void visit(GroupEntry entry) {
			text = "Group Entry";
		}

		public void visit(PublicEntry entry) {
			text = "Not implemented yet";
		}

		public void visit(SystemEntry entry) {
			text = "Not implemented yet";
		}

		public void visit(RewriteSystemEntry entry) {
			text = "Not implemented yet";
		}

		public void visit(DelegatePublicEntry entry) {
			text = "Not implemented yet";
		}

		public void visit(DelegateSystemEntry entry) {
			text = "Not implemented yet";
		}

		public void visit(UriEntry entry) {
			text = entry.getName() + " \u2192 " + entry.getUri();
		}

		public void visit(RewriteUriEntry entry) {
			text = "Not implemented yet";
		}

		public void visit(DelegateUriEntry entry) {
			text = "Not implemented yet";
		}

		public void visit(NextCatalogEntry entry) {
			text = "Not implemented yet";
		}

	}

}
