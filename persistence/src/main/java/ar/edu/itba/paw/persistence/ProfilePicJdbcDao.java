package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProfilePicJdbcDao implements ProfilePicDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final static RowMapper<Image> IMAGE_ROW_MAPPER =
            (rs, rowNum) -> new Image( rs.getString("uname"), rs.getBytes("image_data"), rs.getString("image_type"));

    @Autowired
    public ProfilePicJdbcDao(final DataSource ds) {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("profile_pic");
    }

    @Override
    public Optional<Image> getProfilePicture(String username) {
        final String query = "SELECT * FROM profile_pic JOIN auth ON auth.user_id = profile_pic.user_id WHERE uname = ?";
        Image image = jdbcTemplate.queryForObject(query, IMAGE_ROW_MAPPER, username);
        return Optional.ofNullable(image);
    }

    @Override
    public void uploadProfilePicture(String username, byte[] profilePicture, String type) {
        final String query = "INSERT INTO profile_pic (user_id, image_data, image_type) VALUES ( (SELECT user_id FROM auth WHERE auth.uname= ? ), ?, ? )";
        jdbcTemplate.update(query, username, profilePicture, type);
    }
}