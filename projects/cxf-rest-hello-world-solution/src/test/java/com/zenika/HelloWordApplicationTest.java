package com.zenika;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = HelloWorldApplication.class)
@WebIntegrationTest
public class HelloWordApplicationTest {

    @Test
    public void helloWorldTest() {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target("http://localhost:8080/hello");
        Invocation.Builder builder = target.request();
        String message = builder.get(String.class);
        Assert.assertEquals("Hello world!",message);
    }

}
