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
        JsonNode nodes = target.path("/contacts").request().get(JsonNode.class);
        int totalElements = 12;
        Assert.assertEquals(totalElements, nodes.size());
        JsonNode firstNode = nodes.get(0);
        JsonNode detailLink = firstNode.get("links").get(0);
        String detailUrl = detailLink.get("href").asText();

        JsonNode node = client.target(detailUrl).request().get(JsonNode.class);
        String id = node.get("id").asText();
        Assert.assertTrue(detailUrl.endsWith(id));
    }


}
