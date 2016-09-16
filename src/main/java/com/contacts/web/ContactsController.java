package com.contacts.web;

import com.contacts.domain.Contact;
import com.contacts.exception.ContactServiceException;
import com.contacts.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by SAMarkov on 9/13/2016.
 */
@RestController
public class ContactsController {

    private static final Logger logger = LoggerFactory.getLogger(ContactsController.class);

    private ContactService contactService;

    @RequestMapping(value = "/hello/contacts", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public @ResponseBody
    List<Contact> getContacts(@RequestParam("nameFilter") String nameFilter) throws ContactServiceException {
        logger.info(String.format("got request for contacts with name filter: %s ", nameFilter));
        return contactService.findFiltered(nameFilter);
    }

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }
}
