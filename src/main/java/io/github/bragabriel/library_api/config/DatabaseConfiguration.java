package io.github.bragabriel.library_api.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    String url;
    @Value("${spring.datasource.username}")
    String username;
    @Value("${spring.datasource.password}")
    String password;
    @Value("${spring.datasource.driver-class-name}")
    String driver;

    //@Bean
    public DataSource dataSource(){
        DriverManagerDataSource ds = new DriverManagerDataSource();
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setDriverClassName(driver);
        return ds;
    }

    /**
     * Hiraki configuration
     * @return
     */
    @Bean
    public DataSource hikariDataSource(){

        HikariConfig config = new HikariConfig();
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);
        config.setJdbcUrl(url);

        config.setMaximumPoolSize(10);  // Sets the maximum number of connections in the pool.
        config.setMinimumIdle(1);  // Sets the minimum number of idle connections to maintain in the pool.
        config.setPoolName("library-db-pool");  // Names the connection pool for identification.
        config.setMaxLifetime(600000);  // Sets the maximum lifetime of a connection in the pool (600,000 milliseconds = 10 minutes).
        config.setConnectionTimeout(100000);  // Sets the maximum time to wait for a connection from the pool (100,000 milliseconds = 100 seconds).
        config.setConnectionTestQuery("select 1");  // Defines a SQL query to test the validity of a connection (simple query "select 1").

        return new HikariDataSource(config);
    }
}
