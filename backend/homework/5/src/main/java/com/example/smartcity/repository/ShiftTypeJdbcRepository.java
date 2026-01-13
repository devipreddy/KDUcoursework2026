package com.example.smartcity.repository;

import com.example.smartcity.dto.response.ShiftTypeResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Repository
public class ShiftTypeJdbcRepository {

    private static final Logger LOG = LoggerFactory.getLogger(ShiftTypeJdbcRepository.class);

    private final JdbcTemplate jdbc;

    public ShiftTypeJdbcRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    private static final RowMapper<ShiftTypeResponse> ROW_MAPPER =
            (rs, rowNum) -> new ShiftTypeResponse(
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("description"),
                    rs.getBoolean("active")
            );

    public void insert(String id, String name, String description, String tenantId) {
        String sql = """
                INSERT INTO shift_type (id, name, description, active, tenant_id)
                VALUES (?, ?, ?, true, ?)
                """;
        LOG.debug("Executing insert into shift_type SQL: {} params: id={}, name={}, description={}, tenantId={}", sql, id, name, description, tenantId);
        jdbc.update(sql, id, name, description, tenantId);
    }

    public List<ShiftTypeResponse> findByTenant(String tenantId) {
        String sql = """
                SELECT id, name, description, active
                FROM shift_type
                WHERE tenant_id = ?
                """;
        LOG.debug("Querying shift_type by tenant SQL: {} params: tenantId={}", sql, tenantId);
        List<ShiftTypeResponse> results = jdbc.query(sql, ROW_MAPPER, tenantId);
        LOG.debug("shift_type query returned {} rows for tenantId={}", results.size(), tenantId);
        return results;
    }
}
