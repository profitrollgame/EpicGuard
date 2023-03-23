/*
 * EpicGuard is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EpicGuard is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 */

package me.xneox.epicguard.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import me.xneox.epicguard.core.EpicGuard;
import me.xneox.epicguard.core.Platform;
import me.xneox.epicguard.core.util.VersionUtils;
import me.xneox.epicguard.velocity.listener.*;
import net.byteflux.libby.Library;
import net.byteflux.libby.VelocityLibraryManager;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.nio.file.Path;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Plugin(
        id = "epicguard",
        name = "EpicGuard",
        version = VersionUtils.CURRENT_VERSION,
        description = "Bot protection system for Minecraft servers.",
        url = "https://github.com/xxneox/EpicGuard",
        authors = {"neox", "4drian3d"})
public class EpicGuardVelocity implements Platform {
    @Inject
    private ProxyServer server;
    @Inject
    private Logger logger;
    @Inject
    @DataDirectory
    private Path path;

    private EpicGuard epicGuard;

    @Subscribe
    public void onEnable(ProxyInitializeEvent e) {
        this.loadLibraries();
        this.epicGuard = new EpicGuard(this);

        final var commandManager = this.server.getCommandManager();
        final var meta = commandManager
                .metaBuilder("epicguard")
                .aliases("guard", "epicguardvelocity", "guardvelocity")
                .plugin(this)
                .build();

        commandManager.register(meta, new VelocityCommandHandler(this.epicGuard));

        final var eventManager = this.server.getEventManager();
        eventManager.register(this, new PostLoginListener(this.epicGuard));
        eventManager.register(this, new PreLoginListener(this.epicGuard));
        eventManager.register(this, new DisconnectListener(this.epicGuard));
        eventManager.register(this, new ServerPingListener(this.epicGuard));
        eventManager.register(this, new PlayerSettingsListener(this.epicGuard));
    }

    @Subscribe
    public void onDisable(ProxyShutdownEvent e) {
        this.epicGuard.shutdown();
    }

    @Override
    public @NotNull String platformVersion() {
        return this.server.getVersion().toString();
    }

    @Override
    public @NotNull Logger logger() {
        return this.logger;
    }

    @Override
    public Audience audience(@NotNull UUID uuid) {
        return this.server.getPlayer(uuid).orElse(null);
    }

    @Override
    public void disconnectUser(@NotNull UUID uuid, @NotNull Component message) {
        this.server.getPlayer(uuid).ifPresent(player -> player.disconnect(message));
    }

    @Override
    public void runTaskLater(@NotNull Runnable task, long seconds) {
        this.server.getScheduler().buildTask(this, task).delay(seconds, TimeUnit.SECONDS).schedule();
    }

    @Override
    public void scheduleRepeatingTask(@NotNull Runnable task, long seconds) {
        this.server.getScheduler().buildTask(this, task).repeat(seconds, TimeUnit.SECONDS).schedule();
    }

    private void loadLibraries() {
        final VelocityLibraryManager<EpicGuardVelocity> manager
                = new VelocityLibraryManager<>(this.logger, this.path, this.server.getPluginManager(), this);

        final Library MYSQL = Library.builder()
                .groupId("com.mysql")
                .artifactId("mysql-connector-j")
                .version("8.0.32")
                .id("mysql")
                .build();

        final Library SQLITE = Library.builder()
                .groupId("org{}xerial")
                .artifactId("sqlite-jdbc")
                .version("3.40.1.0")
                .id("sqlite")
                .id("mysql")
                .build();
        final Library hocon = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-hocon")
                .version(VersionUtils.CONFIGURATE)
                .id("configurate-hocon")
                .build();
        final Library confCore = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-core")
                .version(VersionUtils.CONFIGURATE)
                .id("configurate-core")
                .build();
        final Library geantyref = Library.builder()
                .groupId("io{}leangen{}geantyref")
                .artifactId("geantyref")
                .version("1.3.13")
                .id("geantyref")
                .build();
        final Library hikari = Library.builder()
                .groupId("com.zaxxer")
                .artifactId("HikariCP")
                .version(VersionUtils.HIKARI)
                .id("hikari")
                .build();
        final Library compress = Library.builder()
                .groupId("org.apache.commons")
                .artifactId("commons-compress")
                .version(VersionUtils.COMMANDS_COMPRESS)
                .id("hikari")
                .build();
        final Library text = Library.builder()
                .groupId("org.apache.commons")
                .artifactId("commons-text")
                .version(VersionUtils.COMMONS_TEXT)
                .id("hikari")
                .build();

        manager.addMavenCentral();
        manager.loadLibrary(SQLITE);
        manager.loadLibrary(MYSQL);
        manager.loadLibrary(geantyref);
        manager.loadLibrary(confCore);
        manager.loadLibrary(hocon);
        manager.loadLibrary(hikari);
        manager.loadLibrary(compress);
        manager.loadLibrary(text);
    }
}
