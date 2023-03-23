package me.xneox.epicguard.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import me.xneox.epicguard.core.util.VersionUtils;
import net.byteflux.libby.Library;
import net.byteflux.libby.VelocityLibraryManager;
import org.slf4j.Logger;

import java.nio.file.Path;

final class Libraries {
    @Inject
    @DataDirectory
    private Path folderPath;
    @Inject
    private PluginManager pluginManager;
    @Inject
    private EpicGuardVelocity plugin;
    @Inject
    private Logger logger;
    void register() {
        final var manager = new VelocityLibraryManager<>(logger, folderPath, pluginManager, plugin);

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
        final Library CONFIGURATE_HOCON = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-hocon")
                .version(VersionUtils.CONFIGURATE)
                .id("configurate-hocon")
                .build();
        final Library CONFIGURATE_CORE = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-core")
                .version(VersionUtils.CONFIGURATE)
                .id("configurate-core")
                .build();
        final Library GEANTYREF = Library.builder()
                .groupId("io{}leangen{}geantyref")
                .artifactId("geantyref")
                .version("1.3.13")
                .id("geantyref")
                .build();
        final Library HIKARI = Library.builder()
                .groupId("com.zaxxer")
                .artifactId("HikariCP")
                .version(VersionUtils.HIKARI)
                .id("hikari")
                .build();
        final Library COMMONS_COMPRESS = Library.builder()
                .groupId("org.apache.commons")
                .artifactId("commons-compress")
                .version(VersionUtils.COMMANDS_COMPRESS)
                .id("hikari")
                .build();
        final Library COMMONS_TEXT = Library.builder()
                .groupId("org.apache.commons")
                .artifactId("commons-text")
                .version(VersionUtils.COMMONS_TEXT)
                .id("hikari")
                .build();

        manager.addMavenCentral();
        manager.loadLibrary(SQLITE);
        manager.loadLibrary(MYSQL);
        manager.loadLibrary(GEANTYREF);
        manager.loadLibrary(CONFIGURATE_CORE);
        manager.loadLibrary(CONFIGURATE_HOCON);
        manager.loadLibrary(HIKARI);
        manager.loadLibrary(COMMONS_COMPRESS);
        manager.loadLibrary(COMMONS_TEXT);
    }

}
