package org.protege.xmlcatalog.swing;

import java.awt.FlowLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.XMLCatalog;

public class CatalogEditor extends JPanel {
	private static final long serialVersionUID = 4105691012851258776L;

	public CatalogEditor(XMLCatalog catalog, List<EntryEditor> editors) {
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new CatalogJTree(catalog, editors));
	}

	
	public static void main(String[] args) {
		try {
			JFrame frame = new JFrame();
			XMLCatalog catalog = CatalogUtilities.parseDocument(new File("/home/tredmond/Shared/ontologies/simple/catalog-v001.xml").toURI().toURL());
			frame.setLayout(new FlowLayout());
			frame.add(new CatalogEditor(catalog, new ArrayList<EntryEditor>()));
			frame.pack();
			frame.setVisible(true);
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}
}
