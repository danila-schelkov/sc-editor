package dev.donutquine.editor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.jar.Manifest;

public class Version {
    private static final Logger LOGGER = LoggerFactory.getLogger(Version.class);

    private static final String VERSION_DEV = "dev";

    private static String version;

    public static String getVersion() {
        if (version == null) {
            version = findVersion();
        }
        return version;
    }

    private static String findVersion() {
        Class<Version> aClass = Version.class;

        // Honestly, I don't like hardcoded path
        try (InputStream in = aClass.getResourceAsStream("/META-INF/maven/dev.donutquine/sc-editor/pom.properties")) {
            Properties properties = new Properties();
            properties.load(in);
            return properties.getProperty("version");
        } catch (IOException e) {
            LOGGER.error("Can't get maven pom file", e);
        }

        try {
            ClassLoader classLoader = aClass.getClassLoader();
            if (classLoader == null) return VERSION_DEV;

            Enumeration<URL> resources = classLoader.getResources("META-INF/MANIFEST.MF");
            while (resources.hasMoreElements()) {
                try (InputStream is = resources.nextElement().openStream()) {
                    Manifest manifest = new Manifest(is);
                    String version = manifest.getMainAttributes().getValue("SC-Editor-Version");
                    if (version != null) {
                        return version;
                    }
                }
            }
        } catch (Exception e) {
            LOGGER.error("Can't get manifest file", e);
        }

        return VERSION_DEV;
    }
}
