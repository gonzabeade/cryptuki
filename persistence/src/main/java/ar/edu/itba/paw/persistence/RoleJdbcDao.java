package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import javax.xml.stream.util.EventReaderDelegate;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleJdbcDao implements RoleDao{
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Role> ROLE_ROW_MAPPER = ((resultSet, i) ->
            new Role(resultSet.getInt("id"),resultSet.getString("description")));

    @Autowired
    public RoleJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Optional<Role> getRoleByDescription(String description) {
        final String query ="SELECT * FROM user_role WHERE description = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, ROLE_ROW_MAPPER, description));
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }
}
