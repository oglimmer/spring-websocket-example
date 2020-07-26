package de.oglimmer.webserver;

import de.oglimmer.webserver.http.WebSocketConnections;
import de.oglimmer.webserver.logic.ChannelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Smoke test for the websocket functionality.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WebserverApplication.class)
public class WebserverApplicationTests {

    public static final String TEST_PAYLOAD = "foo";

    @LocalServerPort
    private int port;

    @Autowired
    private ChannelRepository channelRepository;
    @Autowired
    private WebSocketConnections webSocketConnections;

    @BeforeEach
    public void setup() {
        channelRepository.createFakeChannel(webSocketConnections);
    }

    @Test
    public void greetingShouldReturnDefaultMessage() throws Exception {
        AtomicBoolean success = new AtomicBoolean(false);
        WebSocketClient clientA = new StandardWebSocketClient();
        clientA.doHandshake(new AbstractWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                Thread.sleep(500);
                session.sendMessage(new TextMessage(TEST_PAYLOAD));
            }
        }, "ws://localhost:" + this.port + "/channels/1/1");
        WebSocketClient clientB = new StandardWebSocketClient();
        clientB.doHandshake(new AbstractWebSocketHandler() {
            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) {
                success.set(TEST_PAYLOAD.equals(message.getPayload()));
            }
        }, "ws://localhost:" + this.port + "/channels/1/2");
        Thread.sleep(1000);
        assertTrue(success.get());
    }

}
