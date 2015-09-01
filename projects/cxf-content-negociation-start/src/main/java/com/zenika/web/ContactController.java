package com.zenika.web;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;

/**
 *
 */
@Path("/contacts")
@Service
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @GET
    @Path("/{id}")
    public Response contact(@PathParam("id") Long id) {
        Contact contact = contactRepository.findOne(id);
        if(contact == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(contact).build();
        }
    }

    @GET
    public List<Contact> contacts() {
        return contactRepository.findAll();
    }

}
