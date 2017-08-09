package org.protege.xmlcatalog.owlapi;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Set;

import junit.framework.TestCase;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLImportsDeclaration;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;

public class IRIMapperTest extends TestCase {
	public final static IRI IMPORT_LOCATION = IRI.create("http://test.org/TestPizzaImport.owl");
	public final static IRI PIZZA_IRI         = IRI.create("http://www.co-ode.org/ontologies/pizza");
	public final static IRI REDIRECT_LOCATION = IRI.create("http://protege.stanford.edu/ontologies/pizza/pizza.owl");

	public void testIRIMapper() throws MalformedURLException, IOException, OWLOntologyCreationException {
		OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
		manager.addIRIMapper(new XMLCatalogIRIMapper(new File("src/test/resources/owl/redirect-to-pizza.xml")));
		OWLOntology ontology = manager.loadOntology(IRI.create(new File("src/test/resources/owl/TestFunnyPizzaImport.owl")));
		Set<OWLImportsDeclaration> importDeclarations = ontology.getImportsDeclarations();
		assertEquals(1, importDeclarations.size());
		assertEquals(IMPORT_LOCATION, importDeclarations.iterator().next().getIRI());
		Set<OWLOntology> importedOntologies = ontology.getImports();
		assertEquals(1, importedOntologies.size());
		OWLOntology pizzaOntology = importedOntologies.iterator().next();
		assertEquals(PIZZA_IRI, pizzaOntology.getOntologyID().getOntologyIRI());
		assertEquals(REDIRECT_LOCATION, manager.getOntologyDocumentIRI(pizzaOntology));
	}
}
