package org.protege.xmlcatalog.swing;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.XMLCatalog;

public class CatalogEditor extends JPanel {
	private static final long serialVersionUID = 4105691012851258776L;

	public CatalogEditor(XMLCatalog catalog, List<EntryEditor> editors) {
		setLayout(new BorderLayout());
		add(new CatalogJTree(catalog, editors), BorderLayout.CENTER);
	}

	
	public static void main(String[] args) {
		try {
			JFrame frame = new JFrame("Catalog Viewer");
			frame.setLayout(new BorderLayout());
			frame.setPreferredSize(new Dimension(1024, 768));
			JFileChooser jfc = new JFileChooser();
			int ret = jfc.showOpenDialog(frame);
			if (ret == JFileChooser.APPROVE_OPTION) {
				File f = jfc.getSelectedFile();
				XMLCatalog catalog = CatalogUtilities.parseDocument(f.toURI().toURL());
				frame.add(new CatalogEditor(catalog, new ArrayList<EntryEditor>()));
				frame.setVisible(true);
				frame.repaint();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}
}
