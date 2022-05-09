package ar.edu.itba.paw.persistence;

import jdk.nashorn.internal.runtime.options.Option;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:userInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class UserJdbcDaoTest {

    private static final String USERS_TABLE = "users";
    private static final String AUTH_TABLE = "auth";
    private static final int TESTING_INDEX = 0;

    private ArrayList<User.Builder> users;
    private ArrayList<UserAuth> auths;

    @Autowired
    private DataSource ds;

    private UserJdbcDao userJdbcDao;
    private SimpleJdbcInsert jdbcInsertUser;
    private SimpleJdbcInsert jdbcInsertUserAuth;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsertUser = new SimpleJdbcInsert(ds)
                .withTableName(USERS_TABLE)
                .usingGeneratedKeyColumns("id");
        jdbcInsertUserAuth = new SimpleJdbcInsert(ds)
                .withTableName(AUTH_TABLE);
        userJdbcDao = new UserJdbcDao(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USERS_TABLE);

        users= new ArrayList<>();
        users.add(new User.Builder("gbeade@itba.edu.ar").withId(0).withPhoneNumber("12345678").withRatingSum(20).withRatingCount(5).withLastLogin(LocalDateTime.parse("2022-05-01T02:08:03")));
        users.add(new User.Builder("scastagnino@itba.edu.ar").withId(1).withPhoneNumber("87654321").withRatingSum(14).withRatingCount(7).withLastLogin(LocalDateTime.parse("2022-05-01T02:08:03")));

        auths= new ArrayList<>();
        auths.add(new UserAuth.Builder("gbeade", "pass_gbeade").withId(0).withRole("supervisor").withCode(1234).build());
        auths.add(new UserAuth.Builder("scastagnino", "pass_scastagnino").withId(1).withRole("seller").withCode(4321).build());
    }


    @Test
    public void createUserTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USERS_TABLE);

        // Execute
        User testedUser = userJdbcDao.createUser(users.get(TESTING_INDEX));

        //Validations
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, USERS_TABLE));

        Assert.assertNotNull(testedUser);

        User originalUser = users.get(TESTING_INDEX).build();
        Assert.assertEquals(originalUser.getId(), testedUser.getId());
        Assert.assertEquals(originalUser.getPhoneNumber(), testedUser.getPhoneNumber());
        Assert.assertEquals(originalUser.getRatingCount(), testedUser.getRatingCount());
        Assert.assertEquals(originalUser.getRatingSum(), testedUser.getRatingSum());
        Assert.assertEquals(originalUser.getEmail(), testedUser.getEmail());
        Assert.assertEquals(originalUser.getLastLogin(), testedUser.getLastLogin());

    }

    @Test
    public void getUserByEmailTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USERS_TABLE);
        for(User.Builder user: users){
            insertUser(user.build());
        }

        // Execute
        Optional<User> testedUser = userJdbcDao.getUserByEmail(users.get(TESTING_INDEX).getEmail());

        //Validations
        Assert.assertTrue(testedUser.isPresent());
        Assert.assertEquals(users.get(TESTING_INDEX).build(), testedUser.get());
    }

    @Test
    public void getUserByUsernameTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, USERS_TABLE);
        for(User.Builder user: users){
            insertUser(user.build());
        }
        for(UserAuth auth: auths){
            insertAuth(auth);
        }

        // Execute
        Optional<User> testedUser = userJdbcDao.getUserByUsername(auths.get(TESTING_INDEX).getUsername());

        //Validations
        Assert.assertTrue(testedUser.isPresent());
        Assert.assertEquals(users.get(TESTING_INDEX).build(), testedUser.get());
    }

    private void insertUser(User user){
        HashMap<String, Object> userMap = new HashMap<>();

        userMap.put("email", user.getEmail());
        userMap.put("rating_sum", user.getRatingSum());
        userMap.put("rating_count", user.getRatingCount());
        userMap.put("phone_number", user.getPhoneNumber());
        userMap.put("last_login",user.getLastLogin().getYear()+
                "-"+user.getLastLogin().getMonthValue()+
                "-"+user.getLastLogin().getDayOfMonth()+
                " "+user.getLastLogin().getHour()+
                ":"+user.getLastLogin().getMinute()+
                ":"+user.getLastLogin().getSecond());

        jdbcInsertUser.execute(userMap);
    }

    private void insertAuth(UserAuth auth){
        HashMap<String, Object> authMap = new HashMap<>();

        authMap.put("status", UserStatus.valueOf(auth.getUserStatus().toString()).ordinal());
        authMap.put("code", auth.getCode());
        authMap.put("user_id", auth.getId());
        authMap.put("role_id", auth.getId());
        authMap.put("uname", auth.getUsername());
        authMap.put("password", auth.getPassword());

        jdbcInsertUserAuth.execute(authMap);
    }
}
