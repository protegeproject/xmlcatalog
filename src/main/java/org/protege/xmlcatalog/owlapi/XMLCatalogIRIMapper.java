package org.protege.xmlcatalog.owlapi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import org.protege.xmlcatalog.CatalogUtilities;
import org.protege.xmlcatalog.XMLCatalog;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;

public class XMLCatalogIRIMapper implements OWLOntologyIRIMapper {
	private XMLCatalog catalog;
	
	public XMLCatalogIRIMapper(File f) throws MalformedURLException, IOException {
		this(CatalogUtilities.parseDocument(f.toURI().toURL()));
	}
	
	public XMLCatalogIRIMapper(XMLCatalog catalog) {
		this.catalog = catalog;
	}

	@Override
	public IRI getDocumentIRI(IRI original) {
		URI redirect = CatalogUtilities.getRedirect(original.toURI(), catalog);
		if (redirect != null) {
			return IRI.create(redirect);
		}
		else return null;
	}

}
