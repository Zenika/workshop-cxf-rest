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

    @Autowired ContactRepository repo;

    WebClient client;

    @Before
    public void setUp() {
        reset(repo);
        client = WebClient.create(ENDPOINT_ADDRESS);
        WebClient.getConfig(client).getRequestContext().put(LocalConduit.DIRECT_DISPATCH, Boolean.TRUE);
    }

    @Test
    public void contactExists() {
        Long id = 1L;
        when(repo.findOne(id)).thenReturn(new Contact(id, "John", "Doe", 33));

        Response response = client.accept(MediaType.APPLICATION_JSON)
                .path("/contacts/{id}", id)
                .get();

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

        JsonAssert.with(response.getEntity().toString())
                .assertEquals("id",1)
                .assertEquals("firstname", "John")
                .assertEquals("lastname", "Doe")
                .assertEquals("age", 33);
    }

    @Test
    public void contactDoesNotExists() throws Exception {
        Long id = 1L;
        when(repo.findOne(1L)).thenReturn(null);
        Response response = client.path("/contacts/{id}", id).get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
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
        Contact toBeCreated = new Contact(1L,"John","Doe",33);
        when(repo.save(any(Contact.class))).thenReturn(toBeCreated);

        Response response = client.path("/contacts")
              .header("Content-Type", "application/json")
              .post("{\"firstname\":\"John\",\"lastname\":\"Doe\",\"age\":33}");

        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        assertEquals(ENDPOINT_ADDRESS+"/contacts/1",response.getLocation().toString());

        ArgumentCaptor<Contact> contactCaptor = ArgumentCaptor.forClass(Contact.class);
        verify(repo).save(contactCaptor.capture());
        Contact captured = contactCaptor.getValue();
        Assert.assertEquals(toBeCreated.getFirstname(),captured.getFirstname());
        Assert.assertEquals(toBeCreated.getLastname(), captured.getLastname());
        Assert.assertEquals(toBeCreated.getAge(), captured.getAge());
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
