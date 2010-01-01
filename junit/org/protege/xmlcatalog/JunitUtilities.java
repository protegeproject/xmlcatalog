package org.protege.xmlcatalog;

public class JunitUtilities {

    public static boolean isJava5() {
        return System.getProperty("java.version").startsWith("1.5");
    }
}
