package me.xneox.epicguard.sponge.listener;

import com.google.inject.Inject;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.PreLoginHandler;
import org.spongepowered.api.event.EventListenerRegistration;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.network.ServerSideConnection;
import org.spongepowered.plugin.PluginContainer;

public final class PreLoginListener extends PreLoginHandler implements SpongeListener<ServerSideConnectionEvent.Handshake> {
    @Inject
    private EventManager eventManager;
    @Inject
    private PluginContainer pluginContainer;

    @Inject
    public PreLoginListener(final EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public void register() {
        this.eventManager.registerListener(
                EventListenerRegistration
                        .builder(ServerSideConnectionEvent.Handshake.class)
                        .plugin(pluginContainer)
                        .listener(this)
                        .order(Order.DEFAULT)
                        .build()
        );
    }

    @Override
    public void handle(final ServerSideConnectionEvent.Handshake event) {
        final ServerSideConnection connection = event.connection();
        final String address = connection.address().getAddress().getHostAddress();
        final String nickname = connection.profile().name().orElse("");
        this.onPreLogin(address, nickname)
                .ifPresent(result -> event.connection().close(result));
    }
}
