package config;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import java.util.Map;

public class JpaConfig {
    private static EntityManagerFactory factory;

    public JpaConfig() {}

    public static synchronized EntityManagerFactory getFactory() {
        if(factory == null) {
            factory = Persistence.createEntityManagerFactory(
                    "javaBankPU",
                    Map.of(
                            "jakarta.persistence.nonJtaDataSource",
                            DatabaseConfig.getDataSource()
                    )
            );
        }
        return factory;
    }

    public static synchronized void close() {
        if (factory != null && factory.isOpen()) {
            factory.close();
        }
    }
}