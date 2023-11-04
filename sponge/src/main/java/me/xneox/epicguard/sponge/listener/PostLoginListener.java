package me.xneox.epicguard.sponge.listener;

import com.google.inject.Inject;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.PostLoginHandler;
import org.spongepowered.api.event.EventListenerRegistration;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.network.ServerSideConnectionEvent;
import org.spongepowered.api.network.ServerSideConnection;
import org.spongepowered.plugin.PluginContainer;

public final class PostLoginListener extends PostLoginHandler implements SpongeListener<ServerSideConnectionEvent.Login> {
    @Inject
    private EventManager eventManager;
    @Inject
    private PluginContainer pluginContainer;

    public PostLoginListener(final EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public void register() {
        this.eventManager.registerListener(
                EventListenerRegistration
                        .builder(ServerSideConnectionEvent.Login.class)
                        .plugin(pluginContainer)
                        .listener(this)
                        .order(Order.DEFAULT)
                        .build()
        );
    }

    @Override
    public void handle(final ServerSideConnectionEvent.Login event) {
        final ServerSideConnection connection = event.connection();
        this.onPostLogin(connection.profile().uuid(), connection.address().getAddress().getHostAddress());
    }
}
