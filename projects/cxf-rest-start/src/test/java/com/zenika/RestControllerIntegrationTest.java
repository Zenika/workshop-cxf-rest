package com.zenika;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.zenika.model.Contact;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApplication.class)
@WebIntegrationTest
public class RestControllerIntegrationTest {

    Client client = ClientBuilder.newClient()
            .register(new JacksonJaxbJsonProvider());

    WebTarget target = client.target("http://localhost:8080");

    @Test
    public void selectAndFindOne() {
    	// TODO 04 récupérer la liste des contacts (sous forme de Contact[])
    	// noter la propriété target est déjà positionnée sur l'URL de base
    	
    	// TODO 05 vérifier que le nombre de contacts est bon
		// (il y a 12 contacts, voir la classe InMemoryContactRepository)

    	// TODO 06 lancer le test. Il doit passer.
    	
    	// TODO 08 récupérer un contact par son identifiant
		// (prendre l'identifiant du premier contact du tableau de contacts)
    	
    	// TODO 09 vérifier que l'identifiant du contact récupéré est le bon
    	
    	// TODO 10 lancer le test. Il doit passer.
    	
    }

    // TODO 12 enlever @Ignore de la méthode de test
 	@Ignore
    @Test public void crud() {
        int initialCount = target.path("/contacts").request().get(Contact[].class).length;
        Contact contact = new Contact();
        contact.setFirstname("Oncle");
        contact.setLastname("Picsou");
        contact.setAge(100);
        
        // TODO 13 envoyer le contact ci-dessus pour création
        
        // TODO 14 vérifier que le code retour est bien 201 (CREATED)
        
        // TODO 15 vérifier qu'il y a bien un contact en plus
     	// (récupérer la liste des contacts et vérifier le compte)
        
        // TODO 16 lancer le test. Il doit passer.
        
        // TODO 17 récupérer le contact nouvellement créé
        // (grâche à son URI dans la réponse au POST, utiliser client pour positionner contactTarget)
        // assigner le contact récupéré à la variable ci-dessous
        
        WebTarget contactTarget = null; // = client.target(response.getLocation());
     	Contact lookedUpContact = null;
        
     	// TODO 18 vérifier que les propriétés du contact sont correctes
     	// (comparer les propriétés de contact avec celles de lookedUpContact)
     	

     	// TODO 19 lancer le test. Il doit passer.
     	
     	// TODO 21 positionner le nouvel âge sur lookedUpContact et envoyer la mise à jour au serveur		
        int newAge = 90;
        lookedUpContact.setAge(newAge);

        // TODO 22 récupérer le contact pour s'assurer que le nouvel âge a bien été positionné
        
        // TODO 23 s'assurer que le compte de contacts est toujours bon (initialCount + 1)

        // TODO 24 lancer le test. Il doit passer.

        // TODO 26 supprimer le contact
        
        // TODO 27 s'assurer que le compte de contact est bon (revenu à initialCount)

        // TODO Bonus 29 tenter de récupérer le contact et vérifier que l'on récupère une 404
        // (ce contact n'existe plus, puisque nous l'avons créé puis supprimer)
        
        // TODO 30 lancer le test. Il doit passer.
    }

}
