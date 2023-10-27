import org.spongepowered.gradle.plugin.config.PluginLoaders
import org.spongepowered.plugin.metadata.model.PluginDependency

plugins {
    id("org.spongepowered.gradle.plugin")
}

dependencies {
    implementation(projects.core)
    implementation(libs.libby.sponge)
    compileOnly(libs.slf4j)
}

sponge {
    apiVersion("10.0.0")
    license("GPL-3")
    loader {
        name(PluginLoaders.JAVA_PLAIN)
        version("1.0")
    }
    plugin("epicguard") {
        displayName("EpicGuard")
        entrypoint("me.xneox.epicguard.sponge.EpicGuard")
        description(project.description)
        links {
            homepage("https://github.com/4drian3d/EpicGuard")
            source("https://github.com/4drian3d/EpicGuard")
            issues("https://github.com/4drian3d/EpicGuard/issues")
        }
        contributor("4drian3d") {
            description("Lead Developer")
        }
        dependency("spongeapi") {
            loadOrder(PluginDependency.LoadOrder.AFTER)
            optional(false)
        }
    }
}

tasks {
    shadowJar {
        relocate("net.byteflux.libby", "me.xneox.epicguard.velocity.libby")
        relocate("org.spongepowered", "me.xneox.epicguard.libs.org.spongepowered")
        relocate("io.leangen.geantyref", "me.xneox.epicguard.libs.io.leangen.geantyref")
        relocate("com.zaxxer.hikari", "me.xneox.epicguard.libs.com.zaxxer.hikari")
        relocate("com.mysql.cj", "me.xneox.epicguard.libs.mysql")
        relocate("org.xerial", "me.xneox.epicguard.libs.org.xerial")
        relocate("org.apache.commons", "me.xneox.epicguard.libs.commons")
        relocate("me.xdrop.fuzzywuzzy", "me.xneox.epicguard.libs.fuzzywuzzy")
    }
    build {
        dependsOn(shadowJar)
    }
}