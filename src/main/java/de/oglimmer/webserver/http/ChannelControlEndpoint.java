package de.oglimmer.webserver.http;

import de.oglimmer.webserver.configuration.EndpointConfigurator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * Creates the Websocket Endpoint under /channels/{channelId}/{participantId}.
 */
@Slf4j
@Component
@ServerEndpoint(value = "/channels/{channelId}/{participantId}", configurator = EndpointConfigurator.class)
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ChannelControlEndpoint {

    private WebSocketConnections webSocketConnections;

    @OnOpen
    public void onOpen(final Session session,
                       @PathParam("channelId") final String channelId,
                       @PathParam("participantId") final String participantId) {
        log.debug("Received connection from {}/{}", channelId, participantId);
        webSocketConnections.connect(channelId, participantId, session);
    }

    @OnMessage
    public void onMessage(@PathParam("channelId") final String channelId,
                          @PathParam("participantId") final String participantId,
                          final String message) {
        log.debug("Received messages from {}/{} = {}", channelId, participantId, message);
        webSocketConnections.onMessage(channelId, participantId, message);
        //return message + " (from your server)"; // Add this if you always want to send messages back
    }

    @OnError
    public void onError(@PathParam("channelId") final String channelId,
                        @PathParam("participantId") final String participantId,
                        final Throwable t) {
        log.warn("Channel " + channelId + ", participant " + participantId + " failed.", t);
    }

    @OnClose
    public void onClose(final Session session, @PathParam("channelId") final String channelId,
                        @PathParam("participantId") final String participantId) {
        log.debug("Connection closed by {}/{}", channelId, participantId);
        webSocketConnections.disconnect(session);
    }

}
