package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.exception.DuplicateEmailException;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
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
                    .withUsername(resultSet.getString("uname"))
                    .withRatingSum(resultSet.getInt("rating_sum"))
                    .withLastLogin(resultSet.getTimestamp("last_login").toLocalDateTime())
                    .build();

    @Autowired
    public UserJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("users").usingGeneratedKeyColumns("id").usingColumns("email","rating_sum","rating_count","phone_number");
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        final String query ="SELECT * FROM users LEFT OUTER JOIN auth ON id = user_id where email = ?";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, USER_EMAIL_ROW_MAPPER, email));
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }


    @Override
    public User createUser(User.Builder user) {
        final Map<String,Object> args = new HashMap<>();
        args.put("email",user.getEmail());
        args.put("rating_sum",user.getRatingSum());
        args.put("rating_count",user.getRatingCount());
        args.put("phone_number", user.getPhoneNumber());
        int id;
        try {
            id = jdbcInsert.executeAndReturnKey(args).intValue();
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicateEmailException(user.getEmail(), dive);
        }
        catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }

        user.withId(id);
        return user.build();
    }


    @Override
    public Optional<User> getUserByUsername(String username) {
        final String query = "SELECT * FROM users JOIN (SELECT user_id, uname FROM auth WHERE uname = ?) user_auth ON users.id = user_auth.user_id";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, USER_EMAIL_ROW_MAPPER, username));
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void updateLastLogin(String username) {
        try {
            jdbcTemplate.update("UPDATE users SET last_login = NOW() WHERE id IN (SELECT user_id FROM auth WHERE uname = ?)", username);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void incrementUserRating(String username, int rating) {
        final String query = "UPDATE users SET rating_count = rating_count + 1, rating_sum = rating_sum + ? WHERE id IN ( SELECT user_id FROM auth WHERE uname = ?)";

        try {
            jdbcTemplate.update(query, rating, username);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }


}
