package de.oglimmer.webserver.http;

import de.oglimmer.webserver.logic.Channel;
import de.oglimmer.webserver.logic.ChannelRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Keeps a reference to all open Websocket connections with a relation to ChannelId and ParticipantId.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class WebSocketConnections {

    @NonNull
    private ChannelRepository channelRepository;

    private Set<WebSocketConnection> connections = Collections.synchronizedSet(new HashSet<>());

    public void connect(final String channelId, final String participantId, final Session session) {
        connections.add(new WebSocketConnection(channelId, participantId, session));
    }

    public void disconnect(final Session session) {
        synchronized (this) {
            this.connections.removeIf(e -> e.getSession() == session);
        }
    }

    public void onMessage(final String channelId, final String participantId, final String message) {
        this.connections.stream()
                .filter(e -> e.getChannelId().equals(channelId) && e.getParticipantId().equals(participantId))
                .forEach(e -> {
                    final Channel channel = channelRepository.getChannel(e.getChannelId());
                    assert channel != null;
                    channel.onMessage(e.getParticipantId(), message);
                });
    }

    public void sendMessage(final Set<String> participantIds, final String message) {
        this.connections.stream()
                .filter(e -> participantIds.contains(e.getParticipantId()))
                .forEach(e -> e.getSession().getAsyncRemote().sendText(message));
    }

}
