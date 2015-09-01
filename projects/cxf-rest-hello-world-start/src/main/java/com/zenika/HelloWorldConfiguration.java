package com.zenika;

import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.Path;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
// TODO 02 Analyser la configuration en lisant les commentaires ci-dessous

// Ces indications sont données à titre informatif, il n'est pas nécessaire
// de comprendre toute cette configuration qui est faite une fois pour toutes
// dans un projet.

// Annotation pour "devenir" une classe de configuration Spring
@Configuration
public class HelloWorldConfiguration {

	// Déclaration de la servlet CXF
	// Il s'agit du point d'entrée HTTP
    @Bean
    public ServletRegistrationBean servlet() {
        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new CXFServlet(), "/*");
        servletRegistration.setLoadOnStartup(1);
        return servletRegistration;
    }

    // Le bus CXF, le composant central de CXF
    // Il se branche sur Spring
    @Bean public SpringBus cxf() {
        return new SpringBus();
    }

    // Le "server" CXF, les contrôleurs se branchent sur ce serveur
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
            factory.setBus(ctx.getBean(SpringBus.class));
            factory.setResourceProviders(resourceProviders);
            return factory.create();
        } else {
            return null;
        }
    }

}
