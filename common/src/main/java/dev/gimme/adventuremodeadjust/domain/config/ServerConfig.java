package dev.gimme.adventuremodeadjust.domain.config;

import java.util.Map;

public abstract class ServerConfig {

    public static ServerConfig INSTANCE;

    /**
     * Gets the mapping of items (regex) to which blocks (regex) they can be placed on.
     */
    public abstract Map<String, String> getCanPlaceOn();

    /**
     * Gets the mapping of items (regex) to which blocks (regex) they can break.
     */
    public abstract Map<String, String> getCanBreak();
}
