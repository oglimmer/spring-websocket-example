package de.oglimmer.webserver.logic;

import de.oglimmer.webserver.http.WebSocketConnections;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Stub of Channel class.
 */
@Data
@RequiredArgsConstructor
public class Channel {

    @NonNull
    private String id;
    @NonNull
    private WebSocketConnections webSocketConnections;

    private Set<Participant> participants = new HashSet<>();

    public void onMessage(final String sourceParticipentId, final String message) {
        // relay message to other(!) participant for now
        participants.stream().filter(e -> !e.getId().equals(sourceParticipentId))
                .forEach(e -> this.webSocketConnections.sendMessage(Collections.singleton(e.getId()), message));
    }

    public void addParticipant(final Participant participant) {
        participants.add(participant);
    }
}

