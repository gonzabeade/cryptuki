package ar.edu.itba.paw.persistence;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class RoleJdbcDaoTest {
    private static final String ROLE_TABLE = "user_role";
    private static final int ROLE_COUNT = 2;
    private static final int TESTED_INDEX = 0;

    private ArrayList<Role> roles;

    @Autowired
    private DataSource ds;

    private RoleJdbcDao roleJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(ROLE_TABLE)
                .usingGeneratedKeyColumns("id");
        roleJdbcDao= new RoleJdbcDao(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ROLE_TABLE);

        roles = new ArrayList<>();
        roles.add(new Role(0, "Bitcoin"));
        roles.add(new Role(1, "Ether"));

    }

    @Test
    public void TestGetCryptocurrency(){
        //Setup
        JdbcTestUtils.deleteFromTables(jdbcTemplate, ROLE_TABLE);
        for(int i=0; i<ROLE_COUNT; i++){
            insertRole(roles.get(i));
        }

        // Exercise
        Optional<Role> testedRole= roleJdbcDao.getRoleByDescription(roles.get(TESTED_INDEX).getDescription());

        //Validations
        Assert.assertTrue(testedRole.isPresent());
        Assert.assertEquals(roles.get(TESTED_INDEX).getId(), testedRole.get().getId());
        Assert.assertEquals(roles.get(TESTED_INDEX).getDescription(), testedRole.get().getDescription());
    }

    private void insertRole(Role role){
        HashMap<String, Object> roleMap= new HashMap<>();

        roleMap.put("description", role.getDescription());

        jdbcInsert.execute(roleMap);

    }
}
