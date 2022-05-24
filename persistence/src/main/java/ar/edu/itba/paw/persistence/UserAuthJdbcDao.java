package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.exception.DuplicateUsernameException;
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

//@Repository
public class UserAuthJdbcDao implements UserAuthDao{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<UserAuth> AUTH_ROLE_ROW_MAPPER = (resultSet, i) ->{
        UserAuth.Builder userAuth = new UserAuth.Builder(
                resultSet.getString("uname"),
                resultSet.getString("password"))
                .withId(resultSet.getInt("user_id"))
                .withCode(resultSet.getInt("code"))
                .withRole(resultSet.getString("description"));
                if( resultSet.getInt("status") == 1 )
                    userAuth.withUserStatus(UserStatus.VERIFIED);
                else
                    userAuth.withUserStatus(UserStatus.UNVERIFIED);
                return userAuth.build();
    };

    private static final RowMapper<UserAuth> AUTH_USER_ROW_MAPPER=(resultSet, i) ->{
        UserAuth.Builder userAuth = new UserAuth.Builder(
                resultSet.getString("uname"),
                resultSet.getString("password"))
                .withId(resultSet.getInt("user_id"))
                .withCode(resultSet.getInt("code"))
                ;
                if( resultSet.getInt("status") == 1 )
                    userAuth.withUserStatus(UserStatus.VERIFIED);
                else
                    userAuth.withUserStatus(UserStatus.UNVERIFIED);                ;
        return userAuth.build();
    };



    @Autowired
    public UserAuthJdbcDao(DataSource dataSource,RoleDao roleDao) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auth");
    }


    @Override
    public Optional<UserAuth> getUserAuthByUsername(String username) {
        final String query = "SELECT * FROM (SELECT * FROM auth WHERE uname = ?) AS temp JOIN user_role ON temp.role_id=id ";
        try {
            return Optional.of(jdbcTemplate.queryForObject(query, AUTH_ROLE_ROW_MAPPER, username));
        } catch (EmptyResultDataAccessException erde) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public UserAuth createUserAuth(UserAuth.Builder builder) {
        final Map<String, Object> args = new HashMap<>();
        args.put("user_id", builder.getId());
        args.put("uname", builder.getUsername());
        args.put("password", builder.getPassword());
        args.put("role_id", 2);
        args.put("code", builder.getCode());
        args.put("status", 0);

        try {
            jdbcInsert.execute(args);
        } catch (DataIntegrityViolationException dive) {
            throw new DuplicateUsernameException(builder.getUsername(), dive);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
        return builder.build();
    }

    @Override
    public boolean verifyUser(String username, int code) {
        final String query="UPDATE auth SET status = 1 WHERE uname = ? AND code = ?";

        try {
            return jdbcTemplate.update(query, username, code) == 1;
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        final String query="UPDATE auth SET password = ? WHERE uname = ?";

        try {
            return jdbcTemplate.update(query, newPassword, username) == 1;
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public Optional<UserAuth> getUserAuthByEmail(String email) {
        final String query ="SELECT * FROM auth JOIN (select * from users where email = ?) temp on temp.id = user_id ";

        try {
            return Optional.of(jdbcTemplate.queryForObject(query, AUTH_USER_ROW_MAPPER, email));
        } catch  (EmptyResultDataAccessException erde) {
            return Optional.empty();
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

}
