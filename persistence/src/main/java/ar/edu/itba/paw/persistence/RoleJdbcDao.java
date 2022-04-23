package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class RoleJdbcDao implements RoleDao{
    private JdbcTemplate jdbcTemplate;

    private static final RowMapper<Role> ROLE_ROW_MAPPER = ((resultSet, i) ->
            new Role(resultSet.getInt("id"),resultSet.getString("description")) );

    @Autowired
    public RoleJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }


    @Override
    public Optional<Role> getRoleByDescription(String description) {
        String query ="SELECT * FROM user_role WHERE description = ?";
        List<Role> roles = jdbcTemplate.query(query,ROLE_ROW_MAPPER,description);
        return roles.isEmpty() ? Optional.empty() : Optional.of(roles.get(0))  ;

    }
}
