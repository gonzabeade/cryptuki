package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProfilePicJdbcDao implements ProfilePicDao {
    private final NamedParameterJdbcTemplate namedJdbcTemplate;
    private final JdbcTemplate jdbcTemplate;
    private final static RowMapper<Image> IMAGE_ROW_MAPPER =
            (rs, rowNum) -> new Image( rs.getString("uname"), rs.getBytes("image_data"), rs.getString("image_type"));

    @Autowired
    public ProfilePicJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Optional<Image> getProfilePicture(String username) {
        final String query = "SELECT * FROM profile_pic JOIN auth ON auth.user_id = profile_pic.user_id WHERE uname = ?";

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, IMAGE_ROW_MAPPER, username));
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void uploadProfilePicture(String username, byte[] profilePicture, String type) {
        final String query = "INSERT INTO profile_pic (user_id, image_data, image_type)\n" +
                "VALUES ((SELECT user_id FROM auth WHERE auth.uname= :username), :profile_pic, :type)\n" +
                "ON CONFLICT (user_id) DO UPDATE SET image_data = :profile_pic, image_type = :type\n" +
                "WHERE profile_pic.user_id = (SELECT user_id as uid FROM auth WHERE auth.uname= :username )";

        MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("username", username)
                .addValue("profile_pic", profilePicture)
                .addValue("type", type);

        try {
            namedJdbcTemplate.update(query, map);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }
}