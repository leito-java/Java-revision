package com.leito.taskmanager.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

/** Vérifie en CI que la migration Flyway est réellement compatible avec PostgreSQL. */
@SpringBootTest
@ActiveProfiles("postgres-test")
@EnabledIfEnvironmentVariable(named = "POSTGRES_TEST_URL", matches = ".+")
@Transactional
class PostgresMigrationIntegrationTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void flywayCreatesAUsableTasksTable() {
        Long successfulMigrations = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM flyway_schema_history WHERE success = TRUE",
                Long.class
        );

        jdbcTemplate.update(
                "INSERT INTO tasks (title, completed, priority) VALUES (?, ?, ?)",
                "Tester la migration PostgreSQL",
                false,
                "HIGH"
        );

        String priority = jdbcTemplate.queryForObject(
                "SELECT priority FROM tasks WHERE title = ?",
                String.class,
                "Tester la migration PostgreSQL"
        );

        assertThat(successfulMigrations).isPositive();
        assertThat(priority).isEqualTo("HIGH");
    }
}
