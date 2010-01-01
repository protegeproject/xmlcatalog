package org.protege.xmlcatalog;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import org.protege.xmlcatalog.parser.OuterXmlBaseContext;

public class ResolveWithRoundTripTest extends ResolveTest {

    @Override
    protected XMLCatalog parseCatalog(File location) throws MalformedURLException, IOException {
        XMLCatalog catalog = super.parseCatalog(location);
        File tmpLocation = File.createTempFile("catalog-", ".xml");
        CatalogUtilities.save(catalog, tmpLocation);
        catalog = CatalogUtilities.parseDocument(tmpLocation.toURI().toURL());
        catalog.setXmlBaseContext(new OuterXmlBaseContext(location.toURI()));
        return catalog;
    }
}
