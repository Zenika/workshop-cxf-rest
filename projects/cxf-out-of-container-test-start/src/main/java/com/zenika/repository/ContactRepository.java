package com.zenika.repository;

import com.zenika.model.Contact;

import java.util.List;

/**
 * Created by acogoluegnes on 28/08/15.
 */
public interface ContactRepository {

    Contact findOne(Long id);

    Contact save(Contact contact);

    List<Contact> findAll();

    void delete(Long id);

}
