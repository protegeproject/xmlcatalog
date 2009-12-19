package org.protege.xmlcatalog.exception;

import java.io.IOException;

public class CatalogParseException extends IOException {
	public CatalogParseException() {
		
	}
	
	public CatalogParseException(String msg) {
		super(msg);
	}
	
	public CatalogParseException(Throwable t) {
		super(t.getMessage());
		initCause(t);
	}
	
	public CatalogParseException(String msg, Throwable t) {
		super(msg);
		initCause(t);
	}
}
