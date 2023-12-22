package me.xneox.epicguard.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.plugin.PluginManager;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import me.xneox.epicguard.core.util.Constants;
import net.byteflux.libby.Library;
import net.byteflux.libby.VelocityLibraryManager;
import net.byteflux.libby.relocation.Relocation;
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
                .groupId("com{}mysql")
                .artifactId("mysql-connector-j")
                .version("8.0.32")
                .id("mysql")
                .relocate("com{}mysql{}cj", "me{}xneox{}epicguard{}libs{}mysql")
                .build();
        final Library SQLITE = Library.builder()
                .groupId("org{}xerial")
                .artifactId("sqlite-jdbc")
                .version("3.40.1.0")
                .id("sqlite")
                .relocate("org{}xerial", "me{}xneox{}epicguard{}libs{}org{}xerial")
                .build();
        final Library HIKARI = Library.builder()
                .groupId("com{}zaxxer")
                .artifactId("HikariCP")
                .version(Constants.HIKARI)
                .id("hikari")
                .relocate("com{}zaxxer{}hikari", "me{}xneox{}epicguard{}libs{}com{}zaxxer{}hikari")
                .build();
        final Relocation commonsRelocation = new Relocation(
                "org{}apache{}commons",
                "me{}xneox{}epicguard{}libs{}commons"
        );
        final Library COMMONS_COMPRESS = Library.builder()
                .groupId("org{}apache{}commons")
                .artifactId("commons-compress")
                .version(Constants.COMMANDS_COMPRESS)
                .id("commons-compress")
                .relocate(commonsRelocation)
                .build();
        final Library COMMONS_TEXT = Library.builder()
                .groupId("org{}apache{}commons")
                .artifactId("commons-text")
                .version(Constants.COMMONS_TEXT)
                .id("commons-text")
                .relocate(commonsRelocation)
                .build();
        final Library MAXMIND_GEOIP = Library.builder()
                .groupId("com.maxmind.geoip2")
                .artifactId("geoip2")
                .version(Constants.GEOIP)
                .id("geoip2")
                .build();
        final Library MAXMIND_DB = Library.builder()
                .groupId("com.maxmind.db")
                .artifactId("maxmind-db")
                .version(Constants.MAXMIND_DB)
                .id("maxmind-db")
                .build();
        final Library JACKSON_ANNOTATIONS = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-annotations")
                .version(Constants.JACKSON)
                .id("jackson-annotations")
                .build();
        final Library JACKSON_CORE = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-core")
                .version(Constants.JACKSON)
                .id("jackson-core")
                .build();
        final Library JACKSON_DATABIND = Library.builder()
                .groupId("com.fasterxml.jackson.core")
                .artifactId("jackson-databind")
                .version(Constants.JACKSON)
                .id("jackson-databind")
                .build();
        final Relocation fuzzywuzzyRelocator = new Relocation(
                "me{}xdrop{}fuzzywuzzy",
                "me{}xneox{}epicguard{}libs{}fuzzywuzzy"
        );
        final Library FUZZYWUZZY = Library.builder()
                .groupId("me.xdrop")
                .artifactId("fuzzywuzzy")
                .version(Constants.FUZZYWUZZY)
                .id("fuzzywuzzy")
                .relocate(fuzzywuzzyRelocator)
                .build();
        final Relocation cloudRelocation =
                new Relocation(
                        "cloud{}commandframework",
                        "me.xneox.epicguard{}libs{}cloud");
        final Library cloudVelocity = Library.builder()
                .groupId("cloud{}commandframework")
                .artifactId("cloud-velocity")
                .version(Constants.CLOUD)
                .id("cloudVelocity")
                .relocate(cloudRelocation)
                .build();
        final Library cloudBrigadier = Library.builder()
                .groupId("cloud{}commandframework")
                .artifactId("cloud-brigadier")
                .version(Constants.CLOUD)
                .id("cloudBrigadier")
                .relocate(cloudRelocation)
                .build();
        final Library cloudCore = Library.builder()
                .groupId("cloud{}commandframework")
                .artifactId("cloud-core")
                .version(Constants.CLOUD)
                .id("cloudCore")
                .relocate(cloudRelocation)
                .build();
        final Library cloudServices = Library.builder()
                .groupId("cloud{}commandframework")
                .artifactId("cloud-services")
                .version(Constants.CLOUD)
                .id("cloudServices")
                .relocate(cloudRelocation)
                .build();
        final Library apiGuardian = Library.builder()
                .groupId("org.apiguardian")
                .artifactId("apiguardian-api")
                .version("1.1.2")
                .id("apiguardian")
                .build();

        manager.addMavenCentral();
        loadLibraries(
                manager,
                SQLITE,
                MYSQL,
                HIKARI,
                COMMONS_COMPRESS,
                COMMONS_TEXT,
                MAXMIND_GEOIP,
                MAXMIND_DB,
                JACKSON_ANNOTATIONS,
                JACKSON_CORE,
                JACKSON_DATABIND,
                FUZZYWUZZY,
                cloudCore,
                cloudBrigadier,
                cloudServices,
                cloudVelocity,
                apiGuardian
        );

    }

    private void loadLibraries(VelocityLibraryManager<EpicGuardVelocity> manager, Library... libraries) {
        for (final Library library : libraries) {
            manager.loadLibrary(library);
        }
    }

}
