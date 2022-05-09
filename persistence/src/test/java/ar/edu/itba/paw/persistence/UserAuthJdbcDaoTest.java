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
@Sql(scripts = {"classpath:userAuthInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class UserAuthJdbcDaoTest {

    private static final String AUTH_TABLE = "auth";
    private static final int ROLE_ID = 1;
    private static final int TESTING_INDEX = 0;

    private ArrayList<UserAuth.Builder> auths;
    private ArrayList<User> users;

    @Autowired
    private DataSource ds;

    private UserAuthJdbcDao authJdbcDao;
    private RoleJdbcDao roleJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(AUTH_TABLE)
                .usingGeneratedKeyColumns("id");
        roleJdbcDao = new RoleJdbcDao(ds);
        authJdbcDao = new UserAuthJdbcDao(ds, roleJdbcDao);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, AUTH_TABLE);

        users= new ArrayList<>();
        users.add(new User.Builder("gbeade@itba.edu.ar").withId(0).withPhoneNumber("12345678").withRatingSum(20).withRatingCount(5).withLastLogin(LocalDateTime.parse("2022-05-01T02:08:03")).build());
        users.add(new User.Builder("scastagnino@itba.edu.ar").withId(1).withPhoneNumber("87654321").withRatingSum(14).withRatingCount(7).withLastLogin(LocalDateTime.parse("2022-05-01T02:08:03")).build());

        auths= new ArrayList<>();
        auths.add(new UserAuth.Builder("gbeade", "pass_gbeade").withId(0).withRole("supervisor").withCode(1234));
        auths.add(new UserAuth.Builder("scastagnino", "pass_scastagnino").withId(1).withRole("seller").withCode(4321));
    }


    @Test
    public void createUserTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, AUTH_TABLE);

        // Execute
        UserAuth testedAuth = authJdbcDao.createUserAuth(auths.get(TESTING_INDEX));

        //Validations
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, AUTH_TABLE));

        Assert.assertNotNull(testedAuth);

        UserAuth originalAuth = auths.get(TESTING_INDEX).build();
        Assert.assertEquals(originalAuth.getId(), testedAuth.getId());
        Assert.assertEquals(originalAuth.getUsername(), testedAuth.getUsername());
        Assert.assertEquals(originalAuth.getCode(), testedAuth.getCode());
        Assert.assertEquals(originalAuth.getPassword(), testedAuth.getPassword());
        Assert.assertEquals(originalAuth.getUserStatus(), testedAuth.getUserStatus());
        Assert.assertEquals(originalAuth.getRole(), testedAuth.getRole());

    }

    @Test
    public void getUserByEmailTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, AUTH_TABLE);
        for(UserAuth.Builder auth: auths){
            insertAuth(auth.build());
        }

        // Execute
        Optional<UserAuth> testedAuth = authJdbcDao.getUsernameByEmail(users.get(TESTING_INDEX).getEmail());

        //Validations
        Assert.assertTrue(testedAuth.isPresent());
        Assert.assertEquals(auths.get(TESTING_INDEX).build(), testedAuth.get());
    }

    @Test
    public void getUserAuthByUsernameTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, AUTH_TABLE);
        for(UserAuth.Builder auth: auths){
            insertAuth(auth.build());
        }

        // Execute
        Optional<UserAuth> testedAuth = authJdbcDao.getUserAuthByUsername(auths.get(TESTING_INDEX).getUsername());

        //Validations
        Assert.assertTrue(testedAuth.isPresent());
        Assert.assertEquals(auths.get(TESTING_INDEX).build(), testedAuth.get());
    }

    private void insertAuth(UserAuth auth){
        HashMap<String, Object> authMap = new HashMap<>();

        authMap.put("status", UserStatus.valueOf(auth.getUserStatus().toString()).ordinal());
        authMap.put("code", auth.getCode());
        authMap.put("user_id", auth.getId());
        authMap.put("role_id", ROLE_ID);
        authMap.put("uname", auth.getUsername());
        authMap.put("password", auth.getPassword());

        jdbcInsert.execute(authMap);
    }
}
