package com.contacts.web;

import com.contacts.TestConfig;
import com.contacts.domain.Contact;
import com.contacts.exception.ContactServiceException;
import com.contacts.service.ContactService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Created by SAMarkov on 9/14/2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestConfig.class})
@WebAppConfiguration
public class ContactsControllerTest {

    public static final String NAME_FILTER = "name1";

    @Autowired
    WebApplicationContext wac;

    @Autowired
    private ContactsController contactsController;

    private MockMvc mockMvc;

    private ContactService contactService;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
        contactService = mock(ContactService.class);
        contactsController.setContactService(contactService);
    }

    @Test
    public void getContactsShouldReturnFilteredContacts() throws Exception {
        when(contactService.findFiltered(NAME_FILTER)).thenReturn(getFilteredContacts());
        mockMvc.perform(get("/hello/contacts?nameFilter=name1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].name", is("name2")));

        verify(contactService, times(1)).findFiltered(NAME_FILTER);
        verifyNoMoreInteractions(contactService);
    }

    @Test
    public void findById_TodoEntryNotFound_ShouldRender404View() throws Exception {
        when(contactService.findFiltered(NAME_FILTER)).thenThrow(new ContactServiceException(""));

        mockMvc.perform(get("/hello/contacts?nameFilter=name1"))
                .andExpect(status().isNotFound());

        verify(contactService, times(1)).findFiltered(NAME_FILTER);
    }

    private List<Contact> getFilteredContacts() {
        Contact contact = new Contact();
        contact.setId(2l);
        contact.setName("name2");

        List<Contact> contacts = new ArrayList<>();
        contacts.add(contact);
        return contacts;
    }

    private static class TestUtil {

        public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8")
        );
    }
}