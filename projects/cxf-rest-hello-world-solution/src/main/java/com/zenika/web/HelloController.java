package com.zenika.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.springframework.stereotype.Service;

/**
 *
 */
@Path("/hello")
@Service
public class HelloController {

    @GET
    public String hello() {
        return "Hello world!";
    }

}
