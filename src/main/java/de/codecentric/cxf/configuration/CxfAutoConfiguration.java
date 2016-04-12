package de.codecentric.cxf.configuration;

import java.util.Map;

import org.apache.cxf.Bus;
import org.apache.cxf.bus.spring.SpringBus;
import org.apache.cxf.transport.servlet.CXFServlet;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:spring-boot-starter-cxf.properties")
@Import(XmlValidationConfiguration.class)
public class CxfAutoConfiguration {

    @Value("${soap.service.base.url:/soap-api}")
    private String baseUrl;

    @Value("${cxf.servicelist.title:CXF SpringBoot Starter - service list}")
    private String serviceListTitle;
    
    @Bean
    public ServletRegistrationBean dispatcherServlet() {
        CXFServlet cxfServlet = new CXFServlet();
        
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(cxfServlet, baseUrl + "/*");
        // Add custom Title to CXF´s ServiceList
        Map<String, String> initParameters = servletRegistrationBean.getInitParameters();
        initParameters.put("service-list-title", serviceListTitle);
        
        return servletRegistrationBean;
    }
    
    // If you don´t want to import the cxf.xml-Springbean-Config you have to setUp this Bus for yourself
    // <bean id="cxf" class="org.apache.cxf.bus.spring.SpringBus" destroy-method="shutdown"/>
    @Bean(name = Bus.DEFAULT_BUS_ID)
    public SpringBus springBus() {
        return new SpringBus();
    }
    
    
    /**
     * @return the base-URL, where the WebServices are configured (eihter via property or default-value)
     */
    public String getBaseUrl() {
        return baseUrl;
    }

}