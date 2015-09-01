package com.zenika.web;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingOutInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.client.WebClient;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.transport.local.LocalConduit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import com.jayway.jsonassert.JsonAssert;
import com.zenika.model.Contact;
import com.zenika.repository.ContactRepository;

/**
 * Created by acogoluegnes on 28/08/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
@ActiveProfiles("test")
public class ContactControllerTest {

    static String ENDPOINT_ADDRESS = "local://contacts";

    // TODO 01 lire les commentaires ci-dessous (initialisation du test)
    // il s'agit de vérifier les propriétés et l'initialisation
    // nécessaires pour le test hors-conteneur
    
    // le repository "mock", qui permet de simuler la base de données
    @Autowired ContactRepository repo;

    // le client CXF "mémoire"
    WebClient client;

    // méthode lancée avant chaque méthode de test
    @Before
    public void setUp() {
    	// on "efface" la mémoire du repository mock 
        reset(repo);
        // on initialise le client CXF mémoire
        // il va lancer de fausses requêtes HTTP. Ces requêtes iront directement sur
        // CXF, sans passer par une connexion HTTP.
        client = WebClient.create(ENDPOINT_ADDRESS);
        WebClient.getConfig(client).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
    }
    
    // fin des commentaires, passer au TO DO suivant.

    @Test
    public void contactExists() {
    	// TODO 03 écrire le test pour la méthode contact du contrôleur
        // on suppose que le contact demandé existe,
    	// il faut donc configurer le repository mock pour retourner
    	// un objet Contact
    	// Ne pas oublier de vérifier le contenu JSON avec JsonAssert.

    	

        // TODO 04 lancer le test. Il doit passer.
    }

    @Test
    public void contactDoesNotExists() throws Exception {
    	// TODO 05 écrire le test pour méthode contact du contrôleur
        // on suppose que le contact n'existe pas
        // il faut donc vérifier que l'on récupère bien une erreur 404

        // TODO 06 lancer le test. Il doit passer. 
    }

    @Test public void contacts() throws Exception {
        when(repo.findAll()).thenReturn(Arrays.asList(
                new Contact(1L, "John", "Doe", 33),
                new Contact(2L, "Jane", "Doe", 30)
        ));

        Response response = client.accept(MediaType.APPLICATION_JSON)
                .path("/contacts")
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        
        JsonAssert.with(response.getEntity().toString())
                .assertEquals("$[0].id", 1)
                .assertEquals("$[0].firstname", "John")
                .assertEquals("$[0].lastname", "Doe")
                .assertEquals("$[0].age", 33)
                .assertEquals("$[1].id", 2)
                .assertEquals("$[1].firstname", "Jane")
                .assertEquals("$[1].lastname", "Doe")
                .assertEquals("$[1].age", 30);
    }

    @Test public void create() throws Exception {
    	// TODO 07 écrire le test pour la méthode create du contrôleur
        
        // bien tester que le controleur retourne le bon statut et le bon entête Location
        

        Contact toBeCreated = new Contact(1L,"John","Doe",33);

        String contactJson = "{\"firstname\":\"John\",\"lastname\":\"Doe\",\"age\":33}";
    	
        // TODO 08 Décommenter le code ci-dessous
        /* Le code pour vérifier que le Contact a bien été "reconstitué" à partir du JSON
        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(repo).save(contactCaptor.capture());
        Contact captured = contactCaptor.getValue();
        Assert.assertEquals(toBeCreated.getFirstname(),captured.getFirstname());
        Assert.assertEquals(toBeCreated.getLastname(), captured.getLastname());
        Assert.assertEquals(toBeCreated.getAge(), captured.getAge());
        */
        
        // TODO 09 Lancer le test. Il doit passer.
    }

    @Test public void update() throws Exception {
        Contact toBeUpdated = new Contact(1L,"John","Doe",33);

        Response response = client.path("/contacts/{id}",1L)
                .header("Content-Type", "application/json")
                .put("{\"id\":1,\"firstname\":\"John\",\"lastname\":\"Doe\",\"age\":33}");

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());

        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(repo).save(contactCaptor.capture());
        Contact captured = contactCaptor.getValue();
        Assert.assertEquals(toBeUpdated.getId(),captured.getId());
        Assert.assertEquals(toBeUpdated.getFirstname(),captured.getFirstname());
        Assert.assertEquals(toBeUpdated.getLastname(),captured.getLastname());
        Assert.assertEquals(toBeUpdated.getAge(),captured.getAge());
    }

    @Test public void deleteContact() throws Exception {
        Long id = 1L;
        Response response = client.path("/contacts/{id}",id)
                .delete();

        assertEquals(Response.Status.NO_CONTENT.getStatusCode(), response.getStatus());
        
        verify(repo).delete(id);
    }

    // TODO 02 lire les commentaires ci-dessous (configuration du test)
    // cette classe de configuration est automatiquement utilisée lors du lancement du test.
    // Elle est auto-suffisante : elle configure à la fois CXF en mode mémoire,
    // le contrôleur à tester et le repository mock.
    // Le conteneur web n'est pas utilisé pour ce test.
    
    @Configuration
    @Profile("test")
    public static class TestConfiguration {

        @Bean public ContactController contactController() {
            return new ContactController();
        }

        @Bean public ContactRepository contactRepository() {
            return Mockito.mock(ContactRepository.class);
        }


        @Bean
        public Server jaxRsServer(ApplicationContext ctx) {
            List<ResourceProvider> resourceProviders = new LinkedList<ResourceProvider>();
            for (String beanName : ctx.getBeanDefinitionNames()) {
                if (ctx.findAnnotationOnBean(beanName, Path.class) != null) {
                    SpringResourceFactory factory = new SpringResourceFactory(beanName);
                    factory.setApplicationContext(ctx);
                    resourceProviders.add(factory);
                }
            }
            if (resourceProviders.size() > 0) {
                JAXRSServerFactoryBean factory = new JAXRSServerFactoryBean();
                factory.setProviders(Arrays.asList(new JacksonJsonProvider()));
                factory.setResourceProviders(resourceProviders);
                factory.setAddress(ENDPOINT_ADDRESS);
                factory.getBus().getOutInterceptors().add(new LoggingOutInterceptor());
                return factory.create();
            } else {
                return null;
            }
        }

    }

}
