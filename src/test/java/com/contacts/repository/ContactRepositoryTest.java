package com.contacts.repository;

import com.contacts.domain.Contact;
import com.google.common.io.Resources;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by SAMarkov on 9/13/2016.
 */
public class ContactRepositoryTest {

    private final ContactRepositoryImpl repository = new ContactRepositoryImpl();
    private JdbcTemplate template;

    @Before
    public void before() throws Exception {
        DataSource dataSource = new DriverManagerDataSource("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1", "sa", "");
        template = new JdbcTemplate(dataSource);
        template.execute(Resources.toString(Resources.getResource("init.sql"), Charset.defaultCharset()));
        fillContacts();
        repository.setJdbcTemplate(template);
    }

    @Test
    public void testFindAll() throws Exception {
        assertEquals(100, repository.findAll().size());
    }

    @Test
    public void saveShouldSaveToDB() throws Exception {
        Contact contact = repository.save(new Contact());
        assertNotNull(contact.getId());
    }

    private void fillContacts() {
        for (int i = 0; i < 100; i++) {
            String name = getName();
            template.execute("insert into contacts (name) values ('" + name + "')");
        }
    }

    public String getName() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            builder.append((char) (97 + (int) (Math.random() * ((123 - 97) + 1))));
        }
        return builder.toString();
    }

}