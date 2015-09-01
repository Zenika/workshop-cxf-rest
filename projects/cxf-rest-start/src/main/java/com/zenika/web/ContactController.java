package com.zenika.web;

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
//TODO 01 annoter le contrôleur avec les annotations appropriées
// URL : /contacts
public class ContactController {

	// TODO 02 injecter le ContactRepository (avec @Autowired}

	// TODO 03 créer une méthode contacts pour récupérer l'ensemble des contacts
	// URL : /contacts
	// opération : GET

	
	// TODO 07 créer une méthode contact pour récupérer un contact avec son identifiant
	// URL : /contacts/{id}
	// opération : GET
	// pour l'instant, retourner juste le contact (pas de gestion de l'erreur 404)
	

	// TODO 11 créer une méthode create pour sauvegarder un contact
	// URL : /contacts
	// opération : POST
	// bien retourner l'entête Location

	
	// TODO 20 créer une méthode update pour mettre à jour un contact
	// URL : /contacts/{id}
	// opération : PUT

	
	// TODO 25 créer une méthode delete pour supprimer un contact
	// URL : /contacts/{id}
	// opération : DELETE

	
	// TODO 28 (Bonus) changer la méthode contact pour qu'elle retourne une 404 si le contact n'a pas été trouvé

}
