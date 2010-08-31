package org.protege.xmlcatalog.swing;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JTree;
import javax.swing.TransferHandler;
import javax.swing.tree.TreeNode;

import org.protege.xmlcatalog.XMLCatalog;

public class CatalogJTree extends JTree {
	private static final long serialVersionUID = -8502646155443727063L;

	public CatalogJTree(XMLCatalog catalog, List<EntryEditor>  editors) {
		super(new CatalogTreeNode(catalog, editors), true);
		setCellRenderer(new AltCellRenderer());
		setDragEnabled(true);
		setTransferHandler(new MyTransferHandler());
	}
	
	private class MyTransferHandler extends TransferHandler {

		private static final long serialVersionUID = 1160492460896419035L;

		protected Transferable createTransferable(JComponent c) {
			if (!(c instanceof CatalogJTree)) {
				return null;
			}
			CatalogJTree tree = (CatalogJTree) c;
			Object o = tree.getLastSelectedPathComponent();
			if (!(o instanceof EntryTreeNode)) {
				return null;
			}
			final EntryTreeNode e = (EntryTreeNode) o;
			return new Transferable() {
				
				public boolean isDataFlavorSupported(DataFlavor flavor) {
					return flavor.getRepresentationClass().equals(EntryTreeNode.class);
				}
				
				public DataFlavor[] getTransferDataFlavors() {
					return new DataFlavor[] { new DataFlavor(EntryTreeNode.class, "XML Catalog Entry")};
				}
				
				public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
					return e;
				}
			};
		}
		
		public boolean canImport(JComponent comp, DataFlavor[] transferFlavors) {
			for (DataFlavor flavor : transferFlavors)  {
				if (EntryTreeNode.class.equals(flavor.getDefaultRepresentationClass())) {
					return true;
				}
			}
			return false;
		}
	}
	
	

}
