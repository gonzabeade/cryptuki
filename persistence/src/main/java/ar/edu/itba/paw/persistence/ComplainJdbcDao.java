package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.ComplainFilter;
import ar.edu.itba.paw.OfferFilter;
import ar.edu.itba.paw.exception.UncategorizedPersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collection;
import java.util.Collections;

@Repository
public class ComplainJdbcDao implements ComplainDao {

    private NamedParameterJdbcTemplate namedJdbcTemplate;
    private JdbcTemplate jdbcTemplate;

    private static RowMapper<Complain> COMPLAIN_ROW_MAPPER =
            (rs, i) -> new Complain.Builder(
                    rs.getString("complainer_uname"))
                    .withTradeId(rs.getInt("trade_id"))
                    .withComplainId(rs.getInt("complain_id"))
                    .withDate(rs.getTimestamp("complain_date").toLocalDateTime().toLocalDate())
                    .withComplainStatus( ComplainStatus.valueOf(rs.getString("status")))
                    .withComplainerComments(rs.getString("complainer_comments"))
                    .withModeratorComments(rs.getString("moderator_comments"))
                    .withModerator(rs.getString("moderator_uname"))
                    .build();


    @Autowired
    public ComplainJdbcDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    private static MapSqlParameterSource toMapSqlParameterSource(ComplainFilter filter) {
        return new MapSqlParameterSource()
                .addValue("complainer_uname", filter.getComplainerUsername().orElse(null))
                .addValue("from_date", filter.getFrom().orElse(null))
                .addValue("to_date", filter.getTo().orElse(null))
                .addValue("complain_status", filter.getComplainStatus().isPresent() ? filter.getComplainStatus().get().toString() : null)
                .addValue("moderator_uname", filter.getModeratorUsername().orElse(null))
                .addValue("offer_id", filter.getOfferId().isPresent() ? filter.getOfferId().getAsInt() : null)
                .addValue("trade_id", filter.getTradeId().isPresent() ? filter.getTradeId().getAsInt() : null)
                .addValue("complain_id", filter.getComplainId().isPresent() ? filter.getComplainId().getAsInt() : null)
                .addValue("limit", filter.getPageSize())
                .addValue("offset", filter.getPage()*filter.getPageSize());
    }

    private static MapSqlParameterSource toMapSqlParameterSource(Complain.Builder builder) {
        return new MapSqlParameterSource()
                .addValue("complainer_uname", builder.getComplainer())
                .addValue("complain_status", builder.getStatus() == null ? null : builder.getStatus().toString())
                .addValue("moderator_uname", builder.getModerator())
                .addValue("trade_id", builder.getTradeId())
                .addValue("complainer_comments", builder.getComplainerComments())
                .addValue("moderator_comments", builder.getModeratorComments());
    }


    @Override
    public Collection<Complain> getComplainsBy(ComplainFilter filter) {
        final String query = "SELECT *\n" +
                "FROM complain_complete\n" +
                "WHERE\n" +
                "    (COALESCE(:trade_id) IS NULL OR trade_id = :trade_id) AND\n" +
                "    (COALESCE(:complain_status) IS NULL OR status = :complain_status) AND\n" +
                "    (COALESCE(:moderator_uname) IS NULL OR moderator_uname = :moderator_uname) AND\n" +
                "    (COALESCE(:complainer_uname) IS NULL OR complainer_uname = :complainer_uname) AND\n" +
                "    (COALESCE(:from_date) IS NULL OR  complain_date >= :from_date) AND\n" +
                "    (COALESCE(:to_date) IS NULL OR  complain_date <= :to_date) AND\n" +
                "    (COALESCE(:offer_id) IS NULL OR  offer_id = :offer_id) AND\n" +
                "    (COALESCE(:complain_id) IS NULL OR complain_id = :complain_id)\n" +
                "    LIMIT :limit OFFSET :offset;";

        try {
            return Collections.unmodifiableCollection(namedJdbcTemplate.query(query, toMapSqlParameterSource(filter), COMPLAIN_ROW_MAPPER));
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public int countComplainsBy(ComplainFilter filter) {
        final String query = "SELECT COUNT(complain_id)\n" +
                "FROM complain_complete\n" +
                "WHERE\n" +
                "    (COALESCE(:trade_id) IS NULL OR trade_id = :trade_id) AND\n" +
                "    (COALESCE(:complain_status) IS NULL OR status = :complain_status) AND\n" +
                "    (COALESCE(:moderator_uname) IS NULL OR moderator_uname = :moderator_uname) AND\n" +
                "    (COALESCE(:complainer_uname) IS NULL OR complainer_uname = :complainer_uname) AND\n" +
                "    (COALESCE(:from_date) IS NULL OR  complain_date >= :from_date) AND\n" +
                "    (COALESCE(:to_date) IS NULL OR  complain_date <= :to_date) AND\n" +
                "    (COALESCE(:offer_id) IS NULL OR  offer_id = :offer_id) AND\n" +
                "    (COALESCE(:complain_id) IS NULL OR complain_id = :complain_id)\n" +
                "    LIMIT :limit OFFSET :offset;";

        try {
            return namedJdbcTemplate.queryForObject(query, toMapSqlParameterSource(filter), Integer.class);
        } catch (EmptyResultDataAccessException erde) {
            return 0;
        } catch (DataAccessException dae){
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void makeComplain(Complain.Builder complain) {
        final String query = "INSERT INTO complain (trade_id, complainer_id, complainer_comments, status, moderator_comments, moderator_id)\n" +
                "VALUES (:trade_id, (SELECT user_id FROM auth WHERE uname = :complainer_uname), :complainer_comments, :complain_status, :moderator_comments, (SELECT user_id FROM auth WHERE uname = :moderator_uname))";

        try {
            namedJdbcTemplate.update(query, toMapSqlParameterSource(complain));
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void updateComplainStatus(int complainId, ComplainStatus complainStatus) {
        final String query = "UPDATE complain SET status = ? WHERE complain_id = ?";

        try {
            jdbcTemplate.update(query, complainStatus.toString(), complainId);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void updateModerator(int complainId, String username) {
        final String query = "UPDATE complain\n" +
                "SET moderator_id = (SELECT user_id FROM auth WHERE uname = ? )\n" +
                "WHERE complain_id = ?";

        try {
            jdbcTemplate.update(query, username, complainId);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }

    @Override
    public void updateModeratorComment(int complainId, String comments) {
        final String query = "UPDATE complain SET moderator_comments = ? WHERE complain_id = ?";

        try {
            jdbcTemplate.update(query, comments, complainId);
        } catch (DataAccessException dae) {
            throw new UncategorizedPersistenceException(dae);
        }
    }
}
