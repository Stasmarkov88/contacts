package com.contacts.service;

import com.contacts.domain.Contact;
import com.contacts.exception.ContactServiceException;
import com.contacts.repository.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by SAMarkov on 9/13/2016.
 */
@Service
public class ContactServiceImpl implements ContactService {

    private static final Logger logger = LoggerFactory.getLogger(ContactServiceImpl.class);

    private ContactRepository repository;

    @Override
    public List<Contact> findFiltered(String nameFilter) throws ContactServiceException {
        final List<Contact> contacts = getContacts();
        logger.info(String.format("Obtained %d contacts", contacts.size()));

        final List<Contact> filtered = filter(contacts, nameFilter);
        logger.info(String.format("Retain %d contacts after filtering with name filter: '%s'", filtered.size(), nameFilter));
        return filtered;
    }

    private List<Contact> getContacts() throws ContactServiceException {
        try {
            return repository.findAll();
        } catch (Exception e) {
            String message = "error while retrieving contacts";
            logger.error(message, e);
            throw new ContactServiceException(message, e);
        }
    }

    private List<Contact> filter(List<Contact> contacts, String nameFilter) {
        return contacts.parallelStream()
                .filter(contact -> !contact.getName().matches(nameFilter))
                .collect(Collectors.toList());
    }

    @Autowired
    public void setRepository(ContactRepository repository) {
        this.repository = repository;
    }
}
