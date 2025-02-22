package ua.comparus.task.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ua.comparus.task.config.DataSourceProperties;
import ua.comparus.task.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
@Slf4j
public class UserRepository {
    private final Map<String, NamedParameterJdbcTemplate> namedJdbcTemplates;
    private final DataSourceProperties dataSource;

    public UserRepository(Map<String, NamedParameterJdbcTemplate> namedJdbcTemplates,
                          DataSourceProperties dataSource) {
        this.namedJdbcTemplates = namedJdbcTemplates;
        this.dataSource = dataSource;

        log.info("dataSource: {}", dataSource);
    }

    public List<User> fetchUsers(String username, String name, String surname) {
        List<User> allUsers = new ArrayList<>();

        for (var ds : dataSource.getDataSources()) {
            NamedParameterJdbcTemplate namedJdbcTemplate = namedJdbcTemplates.get(ds.getName());
            if (namedJdbcTemplate != null) {
                String query = "SELECT " + ds.getMapping().get("id") + " AS id, " +
                        ds.getMapping().get("username") + " AS username, " +
                        ds.getMapping().get("name") + " AS name, " +
                        ds.getMapping().get("surname") + " AS surname " +
                        "FROM " + ds.getTable() + " WHERE 1=1 ";

                MapSqlParameterSource params = new MapSqlParameterSource();

                if (username != null) {
                    query += "AND " + ds.getMapping().get("username") + " = :username ";
                    params.addValue("username", username);
                }
                if (name != null) {
                    query += "AND " + ds.getMapping().get("name") + " = :name ";
                    params.addValue("name", name);
                }
                if (surname != null) {
                    query += "AND " + ds.getMapping().get("surname") + " = :surname ";
                    params.addValue("surname", surname);
                }

                allUsers.addAll(namedJdbcTemplate.query(query, params,
                        (rs, rowNum) -> new User(
                                rs.getString("id"),
                                rs.getString("username"),
                                rs.getString("name"),
                                rs.getString("surname")
                        )));
            }
        }
        return allUsers;
    }
}