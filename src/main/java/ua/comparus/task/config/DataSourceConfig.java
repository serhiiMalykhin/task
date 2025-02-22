package ua.comparus.task.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class DataSourceConfig {

    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Bean
    @Profile("!test")
    public Map<String, NamedParameterJdbcTemplate> jdbcTemplates() {
        var templates = new HashMap<String, NamedParameterJdbcTemplate>();
        for (var props : dataSourceProperties.getDataSources()) {
            DataSource ds = DataSourceBuilder.create()
                    .url(props.getUrl())
                    .username(props.getUser())
                    .password(props.getPassword())
                    .build();
            templates.put(props.getName(), new NamedParameterJdbcTemplate(ds));
        }
        return templates;
    }

}
