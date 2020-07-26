package de.oglimmer.webserver.http;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.websocket.Session;

/**
 * A Websocket connection a.k.a. session and the channel/participant who created it.
 */
@Data
@AllArgsConstructor
public class WebSocketConnection {

    private String channelId;
    private String participantId;
    private Session session;

}
