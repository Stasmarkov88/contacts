package com.contacts.repository;

import com.contacts.domain.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by SAMarkov on 9/13/2016.
 */
@Repository
public class ContactRepositoryImpl implements ContactRepository {

    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Contact> findAll() {
        return jdbcTemplate.query("select * from contacts", new ContactMapper());
    }

    @Override
    public Contact save(Contact contact) {
        String sql = "INSERT INTO contacts (name) VALUES (?)";

        final KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, contact.getName());
            return ps;
        }, holder);

        contact.setId(holder.getKey().longValue());
        return contact;
    }

    private static final class ContactMapper implements RowMapper<Contact> {
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            Contact contact = new Contact();
            contact.setId(rs.getLong("id"));
            contact.setName(rs.getString("name"));
            return contact;
        }
    }

    @Autowired
    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
