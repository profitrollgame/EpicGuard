package me.xneox.epicguard.sponge.listener;

import com.google.inject.Inject;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.PingHandler;
import org.spongepowered.api.event.EventListenerRegistration;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.server.ClientPingServerEvent;
import org.spongepowered.plugin.PluginContainer;

public final class ServerPingListener extends PingHandler implements SpongeListener<ClientPingServerEvent> {
    @Inject
    private EventManager eventManager;
    @Inject
    private PluginContainer pluginContainer;

    @Inject
    public ServerPingListener(final EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public void register() {
        this.eventManager.registerListener(
                EventListenerRegistration
                        .builder(ClientPingServerEvent.class)
                        .plugin(pluginContainer)
                        .listener(this)
                        .order(Order.DEFAULT)
                        .build()
        );
    }

    @Override
    public void handle(final ClientPingServerEvent event) {
        this.onPing(event.client().address().getAddress().getHostAddress());
    }
}
