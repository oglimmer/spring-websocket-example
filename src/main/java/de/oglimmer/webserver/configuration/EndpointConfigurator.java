package de.oglimmer.webserver.configuration;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.websocket.server.ServerEndpointConfig;

/**
 * Supplies the configurator for the @ServerEndpoint. This class connects two worls: the Spring (application context)
 * side of things with the plain Tomcat (no app context) side.
 */
public class EndpointConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public <T> T getEndpointInstance(final Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        EndpointConfigurator.applicationContext = applicationContext;
    }

}
