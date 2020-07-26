package de.oglimmer.webserver;

import de.oglimmer.webserver.http.WebSocketConnections;
import de.oglimmer.webserver.logic.ChannelRepository;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @SpringBootApplication.
 */
@SpringBootApplication
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class WebserverApplication {

    public static void main(final String[] args) {
        final ConfigurableApplicationContext ctxt = SpringApplication.run(WebserverApplication.class, args);
        createFakeChannel(ctxt);
    }

    /**
     * Remove this after you've added "create/join" channel.
     * @param ctxt Spring's application context
     */
    private static void createFakeChannel(final ConfigurableApplicationContext ctxt) {
        final WebSocketConnections webSocketConnections = ctxt.getBean(WebSocketConnections.class);
        final ChannelRepository channelRepository = ctxt.getBean(ChannelRepository.class);
        channelRepository.createFakeChannel(webSocketConnections);
    }

}
