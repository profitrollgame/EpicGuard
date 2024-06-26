package me.xneox.epicguard.sponge;

import com.google.inject.Inject;
import me.xneox.epicguard.core.util.Constants;
import net.byteflux.libby.Library;
import net.byteflux.libby.SpongeLibraryManager;
import net.byteflux.libby.relocation.Relocation;

public class Libraries {
    @Inject
    private SpongeLibraryManager<EpicGuardSponge> manager;
    public void register() {
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

        final Relocation configurateRelocation = new Relocation(
                "org{}spongepowered",
                "me{}xneox{}epicguard{}libs{}org{}spongepowered"
        );
        final Relocation geantyrefRelocation = new Relocation(
                "io{}leangen{}geantyref",
                "me{}xneox{}epicguard{}libs{}io{}leangen{}geantyref"
        );
        final Library CONFIGURATE_HOCON = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-hocon")
                .version(Constants.CONFIGURATE)
                .id("configurate-hocon")
                .relocate(configurateRelocation)
                .relocate(geantyrefRelocation)
                .build();
        final Library CONFIGURATE_CORE = Library.builder()
                .groupId("org{}spongepowered")
                .artifactId("configurate-core")
                .version(Constants.CONFIGURATE)
                .id("configurate-core")
                .relocate(configurateRelocation)
                .relocate(geantyrefRelocation)
                .build();
        final Library GEANTYREF = Library.builder()
                .groupId("io{}leangen{}geantyref")
                .artifactId("geantyref")
                .version("1.3.13")
                .id("geantyref")
                .relocate(configurateRelocation)
                .relocate(geantyrefRelocation)
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

        manager.addMavenCentral();
        manager.loadLibrary(SQLITE);
        manager.loadLibrary(MYSQL);
        manager.loadLibrary(GEANTYREF);
        manager.loadLibrary(CONFIGURATE_CORE);
        manager.loadLibrary(CONFIGURATE_HOCON);
        manager.loadLibrary(HIKARI);
        manager.loadLibrary(COMMONS_COMPRESS);
        manager.loadLibrary(COMMONS_TEXT);
        manager.loadLibrary(MAXMIND_GEOIP);
        manager.loadLibrary(MAXMIND_DB);
        manager.loadLibrary(JACKSON_ANNOTATIONS);
        manager.loadLibrary(JACKSON_CORE);
        manager.loadLibrary(JACKSON_DATABIND);
        manager.loadLibrary(FUZZYWUZZY);
    }
}
