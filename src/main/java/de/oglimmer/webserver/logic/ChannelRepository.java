package de.oglimmer.webserver.logic;

import de.oglimmer.webserver.http.WebSocketConnections;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds all channels.
 */
@Service
public class ChannelRepository {

    /**
     * Remove this.
     */
    public static final String ID_1 = "1";
    public static final String ID_2 = "2";

    private final Map<String, Channel> channels = new HashMap<>();

    /**
     * Remove this.
     */
    public void createFakeChannel(final WebSocketConnections webSocketConnections) {
        final Channel channel = new Channel(ID_1, webSocketConnections);
        final Participant participant1 = new Participant(ID_1);
        final Participant participant2 = new Participant(ID_2);
        channel.addParticipant(participant1);
        channel.addParticipant(participant2);
        addChannel(channel);
    }

    public void addChannel(final Channel channel) {
        channels.put(channel.getId(), channel);
    }

    public Channel getChannel(final String channelId) {
        return channels.get(channelId);
    }
}

