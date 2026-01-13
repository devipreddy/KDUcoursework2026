package com.example.smartcity.repository;

import com.example.smartcity.dto.response.UserResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public class UserJdbcRepository {

    private static final Logger LOG = LoggerFactory.getLogger(UserJdbcRepository.class);

    private final JdbcTemplate jdbc;

    public UserJdbcRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<UserResponse> USER_ROW_MAPPER =
            (rs, rowNum) -> new UserResponse(
                    rs.getString("id"),
                    rs.getString("username"),
                    rs.getString("timezone"),
                    rs.getBoolean("logged_in")
            );

    public void insert(String id, String username, String timezone, String tenantId) {
        String sql = """
                INSERT INTO users (id, username, timezone, tenant_id)
                VALUES (?, ?, ?, ?)
                """;
        LOG.debug("Executing insert into users SQL: {} params: id={}, username={}, timezone={}, tenantId={}", sql, id, username, timezone, tenantId);
        jdbc.update(sql, id, username, timezone, tenantId);
    }

    public List<UserResponse> findByTenant(String tenantId) {
        String sql = """
                SELECT id, username, timezone, logged_in
                FROM users
                WHERE tenant_id = ?
                """;
        LOG.debug("Querying users by tenant SQL: {} params: tenantId={}", sql, tenantId);
        List<UserResponse> results = jdbc.query(sql, USER_ROW_MAPPER, tenantId);
        LOG.debug("users query returned {} rows for tenantId={}", results.size(), tenantId);
        return results;
    }

    public int update(String id, String tenantId, String username, String timezone) {
        String sql = """
                UPDATE users
                SET username = ?, timezone = ?
                WHERE id = ? AND tenant_id = ?
                """;
        LOG.debug("Executing update on users SQL: {} params: username={}, timezone={}, id={}, tenantId={}", sql, username, timezone, id, tenantId);
        int updated = jdbc.update(sql, username, timezone, id, tenantId);
        LOG.debug("update affected {} rows for id={}, tenantId={}", updated, id, tenantId);
        return updated;
    }

    public List<UserResponse> findByTenantPaged(
            String tenantId,
            int size,
            int offset,
            String sortDirection
    ) {
        String order = "ASC";
        if ("desc".equalsIgnoreCase(sortDirection)) {
            order = "DESC";
        }

        String sql =
            "SELECT id, username, timezone, logged_in " +
            "FROM users " +
            "WHERE tenant_id = ? " +
            "ORDER BY username " + order + " " +
            "LIMIT ? OFFSET ?";

        LOG.debug("Querying users paged SQL: {} params: tenantId={}, size={}, offset={}, order={}", sql, tenantId, size, offset, order);
        List<UserResponse> results = jdbc.query(sql, USER_ROW_MAPPER, tenantId, size, offset);
        LOG.debug("findByTenantPaged returned {} rows (tenantId={}, pageSize={})", results.size(), tenantId, size);
        return results;
    }



}
