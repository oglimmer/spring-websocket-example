package de.oglimmer.webserver.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * Required configuration for @ServerEndpoint.
 */
@Configuration
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter endpointExporter() {
        return new ServerEndpointExporter();
    }

    @Bean
    public EndpointConfigurator customSpringConfigurator() {
        return new EndpointConfigurator();
    }
}
