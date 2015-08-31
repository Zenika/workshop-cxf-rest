package com.zenika;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.fasterxml.jackson.jaxrs.xml.JacksonJaxbXMLProvider;
import com.zenika.model.Contact;
import org.apache.cxf.jaxrs.client.WebClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApplication.class)
@WebIntegrationTest
public class RestControllerIntegrationTest {

    Client client = ClientBuilder.newBuilder()
            .newClient()
            .register(new JacksonJaxbJsonProvider())
            .register(new JacksonJaxbXMLProvider());

    WebTarget target = client.target("http://localhost:8080");

    @Test
    public void contentNegociation() {
        Contact[] contacts = target.path("/contacts").request().accept(MediaType.APPLICATION_JSON).get(Contact[].class);
        assertEquals(12, contacts.length);
        Long id = contacts[0].getId();
        Contact contact = target.path("/contacts/{id}").resolveTemplate("id",id).request().accept(MediaType.APPLICATION_JSON).get(Contact.class);
        assertEquals(id, contact.getId());

        contacts = target.path("/contacts").request().accept(MediaType.APPLICATION_XML).get(Contact[].class);

        assertEquals(12, contacts.length);

        contact = target.path("/contacts/{id}").resolveTemplate("id", 1L).request().accept(MediaType.APPLICATION_XML).get(Contact.class);
        id = contacts[0].getId();
        assertEquals(id, contact.getId());
    }

}
