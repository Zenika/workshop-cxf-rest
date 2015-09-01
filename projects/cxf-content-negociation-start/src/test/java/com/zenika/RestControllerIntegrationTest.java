package com.zenika;

import static org.junit.Assert.assertEquals;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import com.zenika.model.Contact;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ContentNegociationApplication.class)
@WebIntegrationTest
// TODO 01 décommenter l'annotation @Ignore
@Ignore
public class RestControllerIntegrationTest {

    Client client = ClientBuilder
            .newClient()
            .register(new JacksonJaxbJsonProvider())
            .register(new JacksonJaxbXMLProvider());

    WebTarget target = client.target("http://localhost:8080");

    @Test
    public void contentNegociation() {
    	// TODO 02 Positionner l'entête Accept à application JSON pour les 2 requêtes ci-dessous
        Contact[] contacts = target.path("/contacts").request().get(Contact[].class);
        assertEquals(12, contacts.length);
        Long id = contacts[0].getId();
        Contact contact = target.path("/contacts/{id}").resolveTemplate("id",id).request().get(Contact.class);
        assertEquals(id, contact.getId());

        // TODO 03 Lancer le test. Il doit passer.
        
        // TODO 04 Analyser les requêtes/réponses dans la console
        // retrouver les entêtes Accept et Content-Type
        
        // TODO 05 Envoyer les 2 mêmes requêtes mais en réclamant du XML
        
        // TODO 06 Lancer le test. Il doit passer aussi en XML.
        
        // TODO 07 Analyser les requêtes/réponses dans la console
        // retrouver les entêtes Accept et Content-Type pour le format XML
        
    }

}
