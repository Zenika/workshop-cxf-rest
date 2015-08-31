package com.zenika.web;

import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 */
@Path("/hello")
@Service
public class HelloController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello world!";
    }

}
