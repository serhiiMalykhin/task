package ua.comparus.task.sevice;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import ua.comparus.task.Application;
import ua.comparus.task.config.TestContainerConfig;
import ua.comparus.task.model.User;
import ua.comparus.task.service.UserService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@Testcontainers
@ContextConfiguration(classes = {Application.class, TestContainerConfig.class})
@ActiveProfiles("test")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplate jdbcTemplate1;

    @Autowired
    private JdbcTemplate jdbcTemplate2;

    @BeforeEach
    void setUp() {
        jdbcTemplate1.execute("CREATE TABLE users (user_id SERIAL PRIMARY KEY, login VARCHAR(255), first_name VARCHAR(255), last_name VARCHAR(255))");
        jdbcTemplate1.execute("INSERT INTO users (login, first_name, last_name) VALUES ('user1', 'John', 'Doe'), ('user2', 'Alice', 'Smith')");

        jdbcTemplate2.execute("CREATE TABLE user_table (ldap_login VARCHAR(255) PRIMARY KEY, name VARCHAR(255), surname VARCHAR(255))");
        jdbcTemplate2.execute("INSERT INTO user_table (ldap_login, name, surname) VALUES ('user3', 'Bob', 'Brown'), ('user4', 'Eve', 'White')");
    }

    @Test
    void testFetchUsersWithFilters() {
        List<User> users = userService.getAllUsers("user1", null, null);
        assertEquals(1, users.size());
        assertEquals("user1", users.get(0).getUsername());

        users = userService.getAllUsers(null, "Alice", null);
        assertEquals(1, users.size());
        assertEquals("Alice", users.get(0).getName());

        users = userService.getAllUsers(null, null, "Doe");
        assertEquals(1, users.size());

        users = userService.getAllUsers(null, null, null);
        assertEquals(4, users.size());
    }
}