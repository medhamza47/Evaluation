package ma.projet.util;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

public final class HibernateUtil {
    private static final Map<String, EntityManagerFactory> FACTORIES = new ConcurrentHashMap<>();

    private HibernateUtil() {
    }

    public static EntityManagerFactory getEntityManagerFactory(String persistenceUnit) {
        return FACTORIES.computeIfAbsent(persistenceUnit, HibernateUtil::createFactory);
    }

    public static void shutdown(String persistenceUnit) {
        EntityManagerFactory factory = FACTORIES.remove(persistenceUnit);
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }

    private static EntityManagerFactory createFactory(String persistenceUnit) {
        Properties properties = new Properties();
        try (InputStream input = HibernateUtil.class.getClassLoader()
                .getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new IllegalStateException("application.properties was not found on the classpath");
            }
            properties.load(input);
        } catch (IOException exception) {
            throw new IllegalStateException("Unable to load application.properties", exception);
        }
        for (String key : properties.stringPropertyNames()) {
            String override = System.getProperty(key);
            if (override != null) {
                properties.setProperty(key, override);
            }
        }
        return Persistence.createEntityManagerFactory(persistenceUnit, properties);
    }
}
