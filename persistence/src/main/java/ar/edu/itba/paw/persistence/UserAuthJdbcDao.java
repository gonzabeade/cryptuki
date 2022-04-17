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
public class UserAuthJdbcDao implements UserAuthDao{

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert jdbcInsert;

    private static final RowMapper<UserAuth> USER_AUTH_USERNAME_ROW_MAPPER = ((resultSet, i) ->
             new UserAuth.Builder(resultSet.getString("uname"),
                    resultSet.getString("password")).id(resultSet.getInt("user_id")).role(resultSet.getString("description")).build());

    @Autowired
    public UserAuthJdbcDao(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("auth");
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
        args.put("role_id",getIdOfRole(userAuth.getRoleDescriptor()));
        jdbcInsert.execute(args);
        return userAuth.build();
    }

    private Integer getIdOfRole(String roleDescriptor){

        String query = "SELECT * FROM user_role WHERE description = ?";

        List<Integer> id = jdbcTemplate.query(query, (resultSet, i) ->
             resultSet.getInt("id")
        ,roleDescriptor);

//        if(id.size() != 1 )
            //Invalid role
        return id.get(0);
    }

}
