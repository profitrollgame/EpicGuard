package me.xneox.epicguard.sponge.listener;

import com.google.inject.Inject;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.DisconnectHandler;
import org.spongepowered.api.entity.living.player.server.ServerPlayer;
import org.spongepowered.api.event.EventListenerRegistration;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.plugin.PluginContainer;

public final class DisconnectListener extends DisconnectHandler implements SpongeListener<ServerSideConnectionEvent.Disconnect> {
    @Inject
    private EventManager eventManager;
    @Inject
    private PluginContainer pluginContainer;

    @Inject
    public DisconnectListener(final EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public void register() {
        this.eventManager.registerListener(
                EventListenerRegistration
                        .builder(ServerSideConnectionEvent.Disconnect.class)
                        .plugin(pluginContainer)
                        .listener(this)
                        .order(Order.DEFAULT)
                        .build()
        );
    }

    @Override
    public void handle(final ServerSideConnectionEvent.Disconnect event) {
        final ServerPlayer player = event.player();
        this.onDisconnect(player.uniqueId());
    }
}
