package ua.comparus.task.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Data
@Component
@ConfigurationProperties
public class DataSourceProperties {

    private List<DataSource> dataSources;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DataSource {
        private String name;
        private String url;
        private String table;
        private String user;
        private String password;
        private Map<String, String> mapping;
    }
}
