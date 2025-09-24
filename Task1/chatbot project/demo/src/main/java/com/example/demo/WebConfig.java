package com.example.demo;

import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfig {

    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
        return factory -> {
            // This is a robust way to add the CSS MIME type
            MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
            mappings.add("css", "text/css");
            factory.setMimeMappings(mappings);
        };
    }
}