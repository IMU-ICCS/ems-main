package eu.passage.upperware.commons.proactive.client;

import lombok.ToString;

@ToString
public enum ProactiveConnectionState {
    CONNECTED(2),
    CONNECTING(1),
    DISCONNECTED(0),
    DISCONNECTING(3);

    private final int code;

    ProactiveConnectionState(int code) {
        this.code = code;
    }

    public static ProactiveConnectionState findByCode(int code) throws RuntimeException {
        for(ProactiveConnectionState state : ProactiveConnectionState.values()) {
            if (state.code==code) {
                return state;
            }
        }
        throw new RuntimeException(String.format("Could not find ProactiveConnectionState for code: %d", code));
    }
}
