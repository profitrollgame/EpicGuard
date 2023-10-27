package me.xneox.epicguard.sponge;

import com.google.inject.Inject;
import com.google.inject.Injector;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.Platform;
import me.xneox.epicguard.core.placeholder.Placeholders;
import me.xneox.epicguard.sponge.listener.*;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.api.Game;
import org.spongepowered.api.MinecraftVersion;
import org.spongepowered.api.Server;
import org.spongepowered.api.config.DefaultConfig;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.StartedEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.plugin.PluginManager;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

@Plugin("epicguard")
public final class EpicGuardSponge implements Platform {
    private static final Logger LOGGER = LoggerFactory.getLogger("EpicGuard");
    @Inject
    private MinecraftVersion version;
    @Inject
    private Game game;
    @Inject
    private PluginManager pluginManager;
    @Inject
    @DefaultConfig(sharedRoot = false)
    private Path configFilePath;
    @Inject
    private PluginContainer pluginContainer;
    @Inject
    private Injector injector;

    private EpicGuard epicGuard;

    @Listener
    public void onServerInitialization(StartedEngineEvent<Server> event) {
        this.injector.getInstance(Libraries.class).register();
        this.epicGuard = new EpicGuard(this, configFilePath);
        this.injector = injector.createChildInjector(
                binder -> binder.bind(EpicGuard.class).toInstance(epicGuard)
        );

        // TODO: Cloud Command
        //this.injector.getInstance(VelocityCommandHandler.class).register();
        Stream.of(
                PostLoginListener.class,
                PreLoginListener.class,
                DisconnectListener.class,
                ServerPingListener.class,
                PlayerSettingsListener.class
        ).map(injector::getInstance).forEach(SpongeListener::register);

        if (this.pluginManager.plugin("miniplaceholders").isPresent()) {
            Placeholders.register();
        }
    }

    @Listener
    public void onServerShutdown(final StoppingEngineEvent<Server> event) {
        this.epicGuard.shutdown();
    }

    @Override
    public @NotNull String platformVersion() {
        return version.name();
    }

    @Override
    public @NotNull Logger logger() {
        return LOGGER;
    }

    @Override
    public @Nullable Audience audience(@NotNull UUID uuid) {
        return game.server().player(uuid).orElse(null);
    }

    @Override
    public void disconnectUser(@NotNull UUID uuid, @NotNull Component message) {
        game.server().player(uuid).ifPresent(player -> player.kick(message));
    }

    @Override
    public void runTaskLater(@NotNull Runnable task, long seconds) {
        game.server().scheduler().submit(Task.builder()
                .plugin(pluginContainer)
                .execute(task)
                .delay(seconds, TimeUnit.SECONDS)
                .build());
    }

    @Override
    public void scheduleRepeatingTask(@NotNull Runnable task, long seconds) {
        game.server().scheduler().submit(Task.builder()
                .plugin(pluginContainer)
                .execute(task)
                .interval(seconds, TimeUnit.SECONDS)
                .build());
    }
}
