package ar.edu.itba.paw.persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class UserAuthJdbcDao implements UserAuthDao{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;
    private RoleDao roleDao;

    private static final RowMapper<UserAuth> USER_AUTH_USERNAME_ROW_MAPPER = ((resultSet, i) ->{
        UserAuth.Builder userAuth = new UserAuth.Builder(
                resultSet.getString("uname"),
                resultSet.getString("password"))
                .id(resultSet.getInt("user_id"))
                .role(resultSet.getString("description"));
                if(resultSet.getInt("status") == 1 )
                    userAuth.userStatus(UserStatus.VERIFIED);
                else
                    userAuth.userStatus(UserStatus.UNVERIFIED);
                return userAuth.build();

    }
            );

    @Autowired
    public UserAuthJdbcDao(DataSource dataSource,RoleDao roleDao) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auth");
        this.roleDao=roleDao;
    }


    @Override
    public Optional<UserAuth> getUserAuthByUsername(String username) {
        String query = "select * from (SELECT * FROM auth where uname = ?) as temp join user_role on temp.role_id=id ";
        List<UserAuth> userAuthList = jdbcTemplate.query(query,USER_AUTH_USERNAME_ROW_MAPPER,username);
        return userAuthList.isEmpty() ? Optional.empty() : Optional.of(userAuthList.get(0));
    }

    @Override
    public UserAuth createUserAuth(UserAuth.Builder userAuth) {
        final Map<String,Object> args = new HashMap<>();
        args.put("user_id",userAuth.getId());
        args.put("uname",userAuth.getUsername());
        args.put("password",userAuth.getPassword());
        args.put("role_id",roleDao.getRoleByDescription(userAuth.getRoleDescriptor()).get().getId());
        args.put("code",userAuth.getCode());
        if(userAuth.getUserStatus().equals(UserStatus.UNVERIFIED))
            args.put("status",0);
        else
            throw new RuntimeException();//can not create verified user.
        jdbcInsert.execute(args);
        return userAuth.build();
    }

    @Override
    public boolean verifyUser(String username, Integer code) {
        String query="UPDATE auth set status=1 where uname=? and code=?";
        return jdbcTemplate.update(query,username,code) == 1;
    }

    @Override
    public int changePassword(String username, Integer code, String password) {
        String query="UPDATE auth set password=? where uname=? and code=?";
        return jdbcTemplate.update(query,password,username,code);
    }




    @Override
    public Optional<UserAuth> getUsernameByEmail(String email) {
        String query ="SELECT * FROM auth join users on id=user_id where email=?";
        List<UserAuth> users = jdbcTemplate.query(query, (resultSet, i) -> new UserAuth.Builder(
                resultSet.getString("uname"),
                resultSet.getString("password"))
                .id(resultSet.getInt("user_id"))
                .code(resultSet.getInt("code"))
                .build(), email);
        return users.isEmpty() ? Optional.empty() : Optional.of(users.get(0))  ;
    }

}
