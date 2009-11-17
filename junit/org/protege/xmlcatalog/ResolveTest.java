package org.protege.xmlcatalog;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;

import javax.xml.transform.TransformerException;

import junit.framework.TestCase;

import org.apache.xml.resolver.CatalogManager;
import org.apache.xml.resolver.tools.CatalogResolver;

public class ResolveTest extends TestCase {

    public void testSimple() throws TransformerException, MalformedURLException, IOException {
        String catalogLocation = "test/catalog01.xml";
        CatalogManager manager = new CatalogManager();
        manager.setCatalogFiles(catalogLocation);
        CatalogResolver resolver = new CatalogResolver(manager);
        XMLCatalog catalog = Util.parseDocument(new File(catalogLocation).toURL(), null);
        URI u = URI.create("http://www.tigraworld.com/protege/ontology1.owl");
        URI redirect = URI.create("file:/home/tredmond/Shared/ontologies/simple/ontology1.owl");
        assertTrue(resolver.resolve(u.toString(), null).getSystemId().equals(redirect.toString()));
        assertTrue(Util.getRedirect(u, catalog).equals(redirect));
    }
}
