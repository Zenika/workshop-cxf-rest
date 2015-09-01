package com.zenika.web;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 *
 */
@Path("/contacts")
@Service
// TODO 02 Ajouter l'annotation de documentation pour la classe
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @GET
    @Path("/{id}")
    // TODO 03 Ajouter l'annotation de documentation pour la méthode contact
    // renseigner les attributs value et response
    @Produces(MediaType.APPLICATION_JSON)
    public Response contact(@PathParam("id") Long id) {
        Contact contact = contactRepository.findOne(id);
        if(contact == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(contact).build();
        }
    }

    // TODO 07 Documenter toutes les méthodes ci-dessous avec @ApiOperation
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Contact> contacts() {
        return contactRepository.findAll();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Contact contact,@Context UriInfo uriInfo) {
        contact = contactRepository.save(contact);
        return Response.created(
                uriInfo.getAbsolutePathBuilder().path(ContactController.class,"contact").build(contact.getId()))
        .build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Contact contact) {
        contactRepository.save(contact);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        contactRepository.delete(id);
        return Response.noContent().build();
    }

}
