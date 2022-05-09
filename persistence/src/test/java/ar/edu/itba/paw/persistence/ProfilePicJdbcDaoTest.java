package ar.edu.itba.paw.persistence;

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
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:profilePicInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class ProfilePicJdbcDaoTest {
    private static final String PIC_TABLE = "profile_picture";

    @Autowired
    private DataSource ds;

    private ProfilePicJdbcDao picJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp(){
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(PIC_TABLE);
        picJdbcDao= new ProfilePicJdbcDao(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, PIC_TABLE);

    }


    @Test
    public void uploadProfilePictureTest(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, PIC_TABLE);

    }

    @Test
    public void getProfilePicture(){
        JdbcTestUtils.deleteFromTables(jdbcTemplate, PIC_TABLE);

    }
}
