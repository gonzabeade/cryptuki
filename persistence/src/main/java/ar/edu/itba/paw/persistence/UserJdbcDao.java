package ar.edu.itba.paw.persistence;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserJdbcDao implements UserDao{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<User> USER_EMAIL_ROW_MAPPER =
            (resultSet, i) -> new User.Builder(
                    resultSet.getString("email"))
                    .withId(resultSet.getInt("id"))
                    .withPhoneNumber(resultSet.getString("phone_number"))
                    .withRatingCount(resultSet.getInt("rating_count"))
                    .withRatingSum(resultSet.getInt("rating_sum"))
                    .withLastLogin(resultSet.getTimestamp("last_login").toLocalDateTime())
                    .build();

    @Autowired
    public UserJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id");
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        String query ="SELECT * FROM users where email=?";
        List<User> users = jdbcTemplate.query(query,USER_EMAIL_ROW_MAPPER,email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0))  ;
    }

    @Override
    public User createUser(User.Builder user) {
        final Map<String,Object> args = new HashMap<>();
        args.put("email",user.getEmail());
        args.put("rating_sum",user.getRatingSum());
        args.put("rating_count",user.getRatingCount());
        args.put("phone_number", user.getPhoneNumber());
        int id = jdbcInsert.executeAndReturnKey(args).intValue();
        user.withId(id);
        return user.build();
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        String query = "SELECT * FROM users JOIN (SELECT user_id, uname FROM auth WHERE uname = ?) user_auth ON users.id = user_auth.user_id";
        return Optional.of(jdbcTemplate.query(query, USER_EMAIL_ROW_MAPPER, username).get(0));
    }

    @Override
    public void updateLastLogin(String username) {
        jdbcTemplate.update("UPDATE users SET last_login = NOW() WHERE id IN (SELECT user_id FROM auth WHERE uname = ?)", username);
    }


}
