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
@Api(value = "/contacts",description = "Gestion des contacts")
public class ContactController {

    @Autowired
    ContactRepository contactRepository;

    @GET
    @Path("/{id}")
    @ApiOperation(
            value = "Récupère un contact par son identifiant",
            response = Contact.class
    )
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
    @ApiOperation(
            value = "Récupère l'ensemble des contacts",
            response = Contact[].class
    )
    public List<Contact> contacts() {
        return contactRepository.findAll();
    }

    @POST
    @ApiOperation(
            value = "Crée un contact"
    )
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Contact contact,@Context UriInfo uriInfo) {
        contact = contactRepository.save(contact);
        return Response.created(
                uriInfo.getAbsolutePathBuilder().path(ContactController.class,"contact").build(contact.getId()))
        .build();
    }

    @PUT
    @Path("/{id}")
    @ApiOperation(
            value = "Met à jour un contact"
    )
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(Contact contact) {
        contactRepository.save(contact);
        return Response.noContent().build();
    }

    @DELETE
    @Path("/{id}")
    @ApiOperation(
            value = "Supprimer un contact."
    )
    public Response delete(@PathParam("id") Long id) {
        contactRepository.delete(id);
        return Response.noContent().build();
    }

}
