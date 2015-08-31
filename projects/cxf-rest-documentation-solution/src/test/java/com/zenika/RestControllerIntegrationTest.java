package com.zenika;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import com.jayway.jsonpath.JsonPath;
import com.zenika.model.Contact;
import org.apache.cxf.jaxrs.client.WebClient;
import org.hamcrest.Matchers;
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
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = RestApplication.class)
@WebIntegrationTest
public class RestControllerIntegrationTest {

    Client client = ClientBuilder.newBuilder()
            .newClient()
            .register(new JacksonJaxbJsonProvider());

    WebTarget target = client.target("http://localhost:8080/app");

    @Test
    public void selectAndFindOne() {
        Contact[] contacts = target.path("/contacts").request().get(Contact[].class);
        assertEquals(12, contacts.length);
        Long id = contacts[0].getId();
        Contact contact = target.path("/contacts/{id}").resolveTemplate("id",id).request().get(Contact.class);
        assertEquals(id, contact.getId());
    }

    @Test public void crud() {
        int initialCount = target.path("/contacts").request().get(Contact[].class).length;
        Contact contact = new Contact();
        contact.setFirstname("Oncle");
        contact.setLastname("Picsou");
        contact.setAge(100);
        Response response = target.path("/contacts").request().post(Entity.entity(contact, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.CREATED.getStatusCode(), response.getStatus());
        int newCount = target.path("/contacts").request().get(Contact[].class).length;
        assertEquals(initialCount + 1, newCount);

        WebTarget contactTarget = client.target(response.getLocation());

        Contact lookedUpContact = contactTarget.request().get(Contact.class);
        assertEquals(contact.getFirstname(), lookedUpContact.getFirstname());
        assertEquals(contact.getLastname(), lookedUpContact.getLastname());
        assertEquals(contact.getAge(), lookedUpContact.getAge());

        int newAge = 90;
        lookedUpContact.setAge(newAge);

        Response putResponse = contactTarget.request().put(Entity.entity(lookedUpContact, MediaType.APPLICATION_JSON));
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(),putResponse.getStatus());
        lookedUpContact = contactTarget.request().get(Contact.class);
        assertEquals(newAge, lookedUpContact.getAge());

        newCount = this.target.path("/contacts").request().get(Contact[].class).length;
        assertEquals(initialCount + 1, newCount);

        Response deleteResponse = contactTarget.request().delete();
        assertEquals(Response.Status.NO_CONTENT.getStatusCode(),deleteResponse.getStatus());

        newCount = this.target.path("/contacts").request().get(Contact[].class).length;
        assertEquals(initialCount, newCount);

        Response responseGet = contactTarget.request().get();
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(),responseGet.getStatus());
    }

    @Test public void swaggerTest() {
        WebTarget swaggerTarget = client.target("http://localhost:8080/app/api-docs");
        JsonNode documentation = swaggerTarget.request().get(JsonNode.class);
        assertEquals(1,documentation.get("apis").size());
        String apiPath = "/contacts";
        assertEquals(apiPath,documentation.get("apis").get(0).get("path").asText());

        swaggerTarget = swaggerTarget.path(apiPath);
        JsonNode apiDocumentation = swaggerTarget.request().get(JsonNode.class);
        assertEquals(2,apiDocumentation.get("apis").size());

        Set<String> apis = new HashSet<>();
        for (JsonNode api : apiDocumentation.get("apis")) {
            apis.add(api.get("path").asText());
        }
        System.out.println(apis);
        assertThat(apis, Matchers.containsInAnyOrder(apiPath, apiPath + "/{id}"));
    }

}
