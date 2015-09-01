package com.zenika;

import com.wordnik.swagger.jaxrs.config.BeanConfig;
import com.wordnik.swagger.jaxrs.listing.ApiDeclarationProvider;
import com.wordnik.swagger.jaxrs.listing.ApiListingResourceJSON;
import com.wordnik.swagger.jaxrs.listing.ResourceListingProvider;
import com.zenika.web.ContactController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 *
 */
// TODO 01 Décommenter la ligne ci-dessous pour activer Swagger
//@Configuration
public class SwaggerConfiguration {

    @Bean public ResourceListingProvider resourceListingProvider() {
        return new ResourceListingProvider();
    }

    @Bean public ApiDeclarationProvider apiDeclarationProvider() {
        return new ApiDeclarationProvider();
    }

    @Bean public ApiListingResourceJSON apiListingResourceJSON() {
        return new ApiListingResourceJSON();
    }

    @Bean public BeanConfig swaggerConfig() {
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setResourcePackage(ContactController.class.getPackage().getName());
        beanConfig.setScan(true);
        beanConfig.setVersion("1.0.0");
        beanConfig.setBasePath("http://localhost:8080/app");
        beanConfig.setTitle("ZenContact");
        beanConfig.setDescription("ZenContact");
        beanConfig.setContact("info@zenika.com");
        beanConfig.setLicense("Copyright Zenika");
        beanConfig.setLicenseUrl("http://zenika.com");
        return beanConfig;
    }


}
