package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.ComplainFilter;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.HashMap;

public class ComplainJdbcDaoTest {
    private static final String COMPLAIN_TABLE = "complain";
    private static final int TEST_INDEX = 0;

    private ArrayList<Complain.Builder> complains;
    private ComplainFilter.Builder filter;

    @Autowired
    private DataSource ds;

    private ComplainJdbcDao complainJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(COMPLAIN_TABLE)
                .usingGeneratedKeyColumns("trade_id");
        complainJdbcDao = new ComplainJdbcDao(ds);
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);

        complains = new ArrayList<>();

        filter = new ComplainFilter.Builder();

    }

    @Test
    public void getComplainsByTest(){

    }

    @Test
    public void countComplainByTest(){

    }

    @Test
    public void makeComplainTest(){

    }
}
