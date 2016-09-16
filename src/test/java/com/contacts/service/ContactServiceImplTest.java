package com.contacts.service;

import com.contacts.domain.Contact;
import com.contacts.repository.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

/**
 * Created by SAMarkov on 9/13/2016.
 */
@RunWith(MockitoJUnitRunner.class)
public class ContactServiceImplTest {

    @Mock
    private ContactRepository repository;

    @InjectMocks
    private ContactServiceImpl service;

    @Test
    public void shouldFilterByNameFilter() throws Exception {
        when(repository.findAll()).thenReturn(getContacts());

        List<Contact> result = service.findFiltered(".*b.*");

        assertEquals(1, result.size());
        assertEquals("aaa", result.get(0).getName());
    }

    @Test
    public void shouldReturnAllWhenNotFiltered() throws Exception {
        when(repository.findAll()).thenReturn(getContacts());

        List<Contact> result = service.findFiltered("abc");

        assertEquals(2, result.size());
    }

    @Test
    public void shouldReturnEmptyListWhenWildcardGiven() throws Exception {
        when(repository.findAll()).thenReturn(getContacts());

        List<Contact> result = service.findFiltered(".*");

        assertTrue(result.isEmpty());
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowRuntimeExceptionWhenRepositoryThrowsException() throws Exception {
        when(repository.findAll()).thenThrow(new SQLException());

        service.findFiltered(".*");
    }

    private List<Contact> getContacts() {
        Contact contact1 = new Contact();
        contact1.setId(1l);
        contact1.setName("aaa");

        Contact contact2 = new Contact();
        contact2.setId(2l);
        contact2.setName("bbb");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact1);
        contacts.add(contact2);
        return contacts;
    }
}