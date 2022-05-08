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
public class UserAuthJdbcDao implements UserAuthDao{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RoleDao roleDao;

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
        this.roleDao=roleDao;
    }


    @Override
    public Optional<UserAuth> getUserAuthByUsername(String username) {
        final String query = "SELECT * FROM (SELECT * FROM auth WHERE uname = ?) AS temp JOIN user_role ON temp.role_id=id ";
        UserAuth userAuthList = jdbcTemplate.queryForObject(query, AUTH_ROLE_ROW_MAPPER, username);
        return Optional.ofNullable(userAuthList);
    }

    @Override
    public UserAuth createUserAuth(UserAuth.Builder builder) {
        final Map<String, Object> args = new HashMap<>();
        args.put("user_id", builder.getId());
        args.put("uname", builder.getUsername());
        args.put("password", builder.getPassword());
        args.put("role_id", roleDao.getRoleByDescription(builder.getRole()).get().getId());
        args.put("code", builder.getCode());
        args.put("status",0);
        jdbcInsert.execute(args);
        return builder.build();
    }

    @Override
    public boolean verifyUser(String username, int code) {
        final String query="UPDATE auth SET status = 1 WHERE uname = ? AND code = ?";
        return jdbcTemplate.update(query, username, code) == 1;
    }

    @Override
    public boolean changePassword(String username, String newPassword) {
        final String query="UPDATE auth SET password = ? WHERE uname = ?";
        return jdbcTemplate.update(query, newPassword, username) == 1;
    }

    @Override
    public Optional<UserAuth> getUsernameByEmail(String email) {
        final String query ="SELECT * FROM auth JOIN (select * from users where email = ?) temp on temp.id = user_id ";
        UserAuth user = jdbcTemplate.queryForObject(query, AUTH_USER_ROW_MAPPER,email);
        return Optional.ofNullable(user);
    }

}
