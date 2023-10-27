package me.xneox.epicguard.sponge.listener;

import com.google.inject.Inject;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.handler.SettingsHandler;
import org.spongepowered.api.event.EventListenerRegistration;
import org.spongepowered.api.event.EventManager;
import org.spongepowered.api.event.Order;
import org.spongepowered.api.event.entity.living.player.PlayerChangeClientSettingsEvent;
import org.spongepowered.plugin.PluginContainer;

public final class PlayerSettingsListener extends SettingsHandler implements SpongeListener<PlayerChangeClientSettingsEvent> {
    @Inject
    private EventManager eventManager;
    @Inject
    private PluginContainer pluginContainer;

    @Inject
    public PlayerSettingsListener(final EpicGuard epicGuard) {
        super(epicGuard);
    }

    @Override
    public void register() {
        this.eventManager.registerListener(
                EventListenerRegistration
                        .builder(PlayerChangeClientSettingsEvent.class)
                        .plugin(pluginContainer)
                        .listener(this)
                        .order(Order.DEFAULT)
                        .build()
        );
    }

    @Override
    public void handle(final PlayerChangeClientSettingsEvent event) {
        this.onSettingsChanged(event.player().uniqueId());
    }
}
