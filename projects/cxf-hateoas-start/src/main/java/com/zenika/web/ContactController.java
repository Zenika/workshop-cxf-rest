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
    // TODO 07 changer la signature pour retourner une liste de ShortContact
    public List<Contact> contacts(@Context UriInfo uriInfo) {
        List<Contact> contacts = contactRepository.findAll();
        
        // TODO 04 initialiser une ArrayList<ShortContact>
		
 		// TODO 05 itérer sur les contacts récupérés via le repository
     		
 		// TODO 06 dans la boucle, pour chaque contact :
 		//   - créer un ShortContact
 		//   - remplir les propriétés firstname et lastname du ShortContact
 		//   - créer un Link pointant vers le détail du Contact (rel=self)
        // (le principe est le même que pour créer l'URL pour l'entête Location
        // lors de la création d'un contact)
 		//   - ajouter le Link au ShortContact
 		//   - ajouter le ShortContact à la liste de ShortContact !
     		
     	// TODO 08 retourner la liste de ShortContact
        return contacts;
    }

    // TODO 03 analyser la classe ShortContact
 	// la liste de contacts sera constituée d'instances de cette classe
 	// noter que ShortContact est une ressource (hérite de ResourceSupport)
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
