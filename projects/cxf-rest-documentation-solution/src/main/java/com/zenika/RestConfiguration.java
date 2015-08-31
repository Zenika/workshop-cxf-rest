package com.zenika;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.interceptor.LoggingInInterceptor;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.lifecycle.ResourceProvider;
import org.apache.cxf.jaxrs.spring.SpringResourceFactory;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.ws.rs.Path;
import javax.ws.rs.ext.MessageBodyWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
@Configuration
public class RestConfiguration {

    @Bean
    public ServletRegistrationBean servlet() {
        ServletRegistrationBean servletRegistration = new ServletRegistrationBean(new CXFServlet(), "/app/*");
        servletRegistration.setLoadOnStartup(1);
        return servletRegistration;
    }


    @Bean public SpringBus cxf() {
        SpringBus bus = new SpringBus();
        //bus.setInInterceptors(Arrays.asList(new LoggingInInterceptor()));
        return bus;
    }

    @Bean
    public Server jaxRsServer(ApplicationContext ctx,List<MessageBodyWriter> providers) {
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
            List<Object> allProviders = new ArrayList<>();
            allProviders.add(new JacksonJsonProvider());
            allProviders.addAll(providers);
            factory.setProviders(allProviders);
            factory.setResourceProviders(resourceProviders);
            return factory.create();
        } else {
            return null;
        }
    }

}
