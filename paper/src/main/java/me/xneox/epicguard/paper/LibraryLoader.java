package me.xneox.epicguard.paper;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import me.xneox.epicguard.core.util.VersionUtils;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class LibraryLoader implements PluginLoader {
    @Override
    public void classloader(@NotNull PluginClasspathBuilder classpathBuilder) {
        final MavenLibraryResolver resolver = new MavenLibraryResolver();

        final RemoteRepository mavenCentral = new RemoteRepository
                .Builder("central", "default", "https://repo1.maven.org/maven2/")
                .build();
        final Dependency configurateHocon = new Dependency(
                new DefaultArtifact("org.spongepowered:configurate-hocon:"+VersionUtils.CONFIGURATE),
                null
        );
        final Dependency hikari = new Dependency(
                new DefaultArtifact("com.zaxxer:HikariCP:"+ VersionUtils.HIKARI),
                null
        );
        final Dependency caffeine = new Dependency(
                new DefaultArtifact("com.github.ben-manes.caffeine:caffeine:"+ VersionUtils.CAFFEINE),
                null
        );
        final Dependency compress = new Dependency(
                new DefaultArtifact("org.apache.commons:commons-compress:"+ VersionUtils.COMMANDS_COMPRESS),
                null
        );
        final Dependency text = new Dependency(
                new DefaultArtifact("org.apache.commons:commons-text:"+ VersionUtils.COMMONS_TEXT),
                null
        );

        resolver.addRepository(mavenCentral);
        resolver.addDependency(configurateHocon);
        resolver.addDependency(hikari);
        resolver.addDependency(caffeine);
        resolver.addDependency(compress);
        resolver.addDependency(text);

        classpathBuilder.addLibrary(resolver);
    }
}
