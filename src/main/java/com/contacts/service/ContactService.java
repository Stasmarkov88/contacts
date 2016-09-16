package com.contacts.service;

import com.contacts.domain.Contact;
import com.contacts.exception.ContactServiceException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by SAMarkov on 9/13/2016.
 */
@Service
public interface ContactService {
    List<Contact> findFiltered(String filterName) throws ContactServiceException;
}
