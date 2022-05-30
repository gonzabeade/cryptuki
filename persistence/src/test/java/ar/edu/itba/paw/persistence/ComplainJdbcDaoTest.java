package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.ComplainFilter;
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
import java.util.Collection;
import java.util.HashMap;

@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = {"classpath:complainInitialState.sql"})
@ContextConfiguration(classes = TestConfig.class)
public class ComplainJdbcDaoTest {
    private static final String COMPLAIN_TABLE = "complain";
    private static final String COMPLAIN_VIEW="complain_complete";
    private static final int TEST_INDEX = 0;

    private ArrayList<Complain.Builder> complains;
    private HashMap<String, Integer> users;
    private ComplainFilter.Builder filter;

    @Autowired
    private DataSource ds;

    @Autowired
    private ComplainJdbcDao complainJdbcDao;
    private SimpleJdbcInsert jdbcInsert;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate = new JdbcTemplate(ds);
        jdbcInsert = new SimpleJdbcInsert(ds)
                .withTableName(COMPLAIN_TABLE)
                .usingGeneratedKeyColumns("complain_id");
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);

        complains = new ArrayList<>();
        complains.add(new Complain
                .Builder("gbeade")
                .withModerator("mdedeu")
                .withModeratorComments("The complaint will be answered soon")
                .withComplainerComments("There was a problem with the offer")
                .withTradeId(0)
                .withComplainStatus(ComplainStatus.ASSIGNED)
                .withDate(LocalDateTime.now())
        );
        complains.add(new Complain
                .Builder("scastagnino")
                .withModerator("mdedeu")
                .withModeratorComments("The complaint will be answered soon")
                .withComplainerComments("There was a problem with the offer")
                .withTradeId(0)
                .withComplainStatus(ComplainStatus.ASSIGNED)
                .withDate(LocalDateTime.now())
        );
        complains.add(new Complain
                .Builder("gbeade")
                .withModerator("mdedeu")
                .withModeratorComments("The complaint will be answered soon")
                .withComplainerComments("There was a problem with the offer")
                .withTradeId(1)
                .withComplainStatus(ComplainStatus.ASSIGNED)
                .withDate(LocalDateTime.now())
        );

        filter = new ComplainFilter.Builder();

        users= new HashMap<>();
        users.put("gbeade", 0);
        users.put("scastagnino", 1);
        users.put("mdedeu", 2);

    }

    @Test
    public void getComplainsByTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);
        filter = new ComplainFilter.Builder();
        for(Complain.Builder complain : complains){
            insertComplain(complain.build());
        }
        int rows = JdbcTestUtils.countRowsInTable(jdbcTemplate,COMPLAIN_VIEW);

        // Execute
        Collection<Complain> testedComplains = complainJdbcDao.getComplainsBy(filter.withPageSize(rows).build());

        // Validations
        Assert.assertNotNull(testedComplains);
        Assert.assertEquals(rows, testedComplains.size());
    }

    @Test
    public void countComplainByTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);
        filter = new ComplainFilter.Builder();
        for(Complain.Builder complain : complains){
            insertComplain(complain.build());
        }

        // Execute
        int testedCount = complainJdbcDao.countComplainsBy(filter.build());

        // Validations
        Assert.assertEquals(JdbcTestUtils.countRowsInTable(jdbcTemplate,COMPLAIN_TABLE), testedCount);
    }

    @Test
    public void makeComplainTest(){
        // Set up
        JdbcTestUtils.deleteFromTables(jdbcTemplate, COMPLAIN_TABLE);

        // Execute
        complainJdbcDao.makeComplain(complains.get(TEST_INDEX));

        // Validations
        Assert.assertEquals(1, JdbcTestUtils.countRowsInTable(jdbcTemplate, COMPLAIN_TABLE));
    }

    private void insertComplain(Complain complain){
        HashMap<String, Object> complainMap = new HashMap<>();

        complainMap.put("trade_id", complain.getTradeId().get());
        complainMap.put("complainer_id", users.get(complain.getComplainer()));
        complainMap.put("complainer_comments", complain.getComplainerComments());
        complainMap.put("moderator_comments", complain.getModeratorComments());
        complainMap.put("moderator_id", users.get(complain.getModerator().getId()));
        complainMap.put("status", complain.getStatus().toString());
        complainMap.put("complain_date",complain.getDate());

        jdbcInsert.execute(complainMap);

    }
}
