package com.zenika;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HateoasApplication.class)
@WebIntegrationTest
public class RestControllerIntegrationTest {

    Client client = ClientBuilder
            .newClient()
            .register(new JacksonJaxbJsonProvider());

    WebTarget target = client.target("http://localhost:8080");

    @Test
    public void selectAndFindOne() throws Exception {
    	// TODO 01 lancer le test et vérifier qu'il fonctionne
        JsonNode nodes = target.path("/contacts").request().get(JsonNode.class);
        int totalElements = 12;
        Assert.assertEquals(totalElements, nodes.size());
        
        // TODO 02 écrire dans la console la variables nodes, lancer le test et analyser le contenu
        // pour l'instant, tous les champs des contacts sont dans la liste.
     	// l'idée du TP est que la liste ne retourne que les champs firstname et
        // lastname, ainsi qu'un lien pointant vers le détail du contact
        
        // TODO 09 relancer le test et analyser le contenu de la réponse
     	// le lien vers le détail du contact doit être présent pour chacun des éléments
        
        // les tâches suivantes sont à faire via l'API de Jackson (pas d'appel REST)
        // voici comment utiliser cette API qui permet de naviguer dans un document JSON
        // ex. : récupérer le premier élément d'un tableau JSON : JsonNode firstNode = nodes.get(0);
        // ex. : récupérer le contenu du champ "links" : JsonNode detailLink = firstNode.get("links") 
     	// TODO 10 récupérer le premier contact à partir de la variable nodes

 		// TODO 11 récupérer le premier et unique lien (champ "links") 

 		// TODO 12 récupérer l'URL de détail (champ "href")
 		String detailUrl = null;
        
 		// TODO 13 décommenter les lignes suivantes, les analyser et lancer le test (il doit passer)
 		/*
 		// requête HTTP sur l'URL récupéré
        JsonNode node = client.target(detailUrl).request().get(JsonNode.class);
        // récupération du champ "id" dans le détail du Contact 
        String id = node.get("id").asText();
        // vérifie que l'URL et l'id récupéré concordent
        Assert.assertTrue(detailUrl.endsWith(id));
        */
    }


}
