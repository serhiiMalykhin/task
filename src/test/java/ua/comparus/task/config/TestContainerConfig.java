package ua.comparus.task.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.HashMap;
import java.util.Map;

@TestConfiguration
@Profile("test")
public class TestContainerConfig {
    static PostgreSQLContainer<?> postgres1 = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("db1")
            .withUsername("testuser")
            .withPassword("testpass");

    static PostgreSQLContainer<?> postgres2 = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("db2")
            .withUsername("testuser")
            .withPassword("testpass");

    static {
        postgres1.start();
        postgres2.start();
    }


    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    public Map<String, NamedParameterJdbcTemplate> jdbcTemplates() {
        Map<String, NamedParameterJdbcTemplate> templates = new HashMap<>();
        for (var props : dataSourceProperties.getDataSources()) {
            if ("data-base-1".equals(props.getName())) {
                templates.put(props.getName(), new NamedParameterJdbcTemplate(new DriverManagerDataSource(postgres1.getJdbcUrl(), "testuser", "testpass")));
            } else if ("data-base-2".equals(props.getName())) {
                templates.put(props.getName(), new NamedParameterJdbcTemplate(new DriverManagerDataSource(postgres2.getJdbcUrl(), "testuser", "testpass")));
            }
        }
        return templates;
    }

    @Bean
    public JdbcTemplate jdbcTemplate1() {
        return new JdbcTemplate(new DriverManagerDataSource(postgres1.getJdbcUrl(), "testuser", "testpass"));
    }

    @Bean
    public JdbcTemplate jdbcTemplate2() {
        return new JdbcTemplate(new DriverManagerDataSource(postgres2.getJdbcUrl(), "testuser", "testpass"));
    }
}