package com.zenika.web;

import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

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
    @Produces(MediaType.APPLICATION_JSON)
    public Response contact(@PathParam("id") Long id) {
        Contact contact = contactRepository.findOne(id);
        if(contact == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(contact).build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ShortContact> contacts(@Context UriInfo uriInfo) {
        List<Contact> contacts = contactRepository.findAll();
        List<ShortContact> resources = new ArrayList<ShortContact>(contacts.size());
        for(Contact contact : contacts) {
            ShortContact resource = new ShortContact();
            resource.setFirstname(contact.getFirstname());
            resource.setLastname(contact.getLastname());

            URI uriDetail = uriInfo.getAbsolutePathBuilder().path(ContactController.class, "contact").build(contact.getId());

            Link detail = new Link(uriDetail.toString(),Link.REL_SELF);
            resource.add(detail);
            resources.add(resource);
        }
        return resources;
    }


    public static class ShortContact extends ResourceSupport {

        private String firstname,lastname;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

    }

}
