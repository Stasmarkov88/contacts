package com.contacts.repository;

import com.contacts.domain.Contact;

import java.util.List;

/**
 * Created by SAMarkov on 9/13/2016.
 */
public interface ContactRepository{
    List<Contact> findAll();
    Contact save(Contact contact);
}
