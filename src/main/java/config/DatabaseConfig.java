package config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;

public class DatabaseConfig {
    private static final HikariDataSource dataSource = createDataSource();

    private static HikariDataSource createDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl("jdbc:postgresql://localhost:5432/javabank");
        config.setUsername("javabank");
        config.setPassword("1234");
        config.setMaximumPoolSize(5);
        config.setMinimumIdle(1);

        return new HikariDataSource(config);
    }


    public static HikariDataSource getDataSource() {
        return dataSource;
    }

    public static void migrate() {
        Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .load()
                .migrate();
    }

    public static void close() {
        dataSource.close();
    }
}
