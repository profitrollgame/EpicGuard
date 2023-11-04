package me.xneox.epicguard.sponge.listener;

import org.spongepowered.api.event.Event;
import org.spongepowered.api.event.EventListener;

public interface SpongeListener<E extends Event> extends EventListener<E> {
    void register();
}
