import com.github.jengelman.gradle.plugins.shadow.ShadowPlugin
import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    java
    alias(libs.plugins.shadow)
}

allprojects {
    apply<JavaPlugin>()
    apply<ShadowPlugin>()

    java {
        toolchain {
            languageVersion.set(JavaLanguageVersion.of(17))
        }
    }
}

subprojects {
    tasks.withType<ShadowJar> {
        val platformName = project.name.capitalize()
        archiveFileName.set("EpicGuard$platformName-${project.version}.jar")

        relocate("org.spongepowered.configurate", "me.xneox.epicguard.libs.configurate")
        relocate("org.apache.commons", "me.xneox.epicguard.libs.apachecommons")
        relocate("com.fasterxml", "me.xneox.epicguard.libs.fasterxml")
        relocate("com.maxmind", "me.xneox.epicguard.libs.maxmind")
        relocate("com.typesafe.config", "me.xneox.epicguard.libs.config")
        relocate("com.zaxxer.hikari", "me.xneox.epicguard.libs.hikari")
        relocate("io.leangen.geantyref", "me.xneox.epicguard.libs.geantyref")

        minimize()

        // Copy compiled platform jars to '/build' directory for convenience.
        doLast {
            copy {
                from(archiveFile)
                into("${rootProject.projectDir}/build")
            }
        }
    }

    tasks.withType<Delete> {
        delete("run")
    }

    tasks.withType<JavaCompile> {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }

    // TODO: replace with plugin.yml plugin to avoid deprecation warning
    // For Waterfall and Paper platforms: set version
    tasks.withType<ProcessResources> {
        filesMatching("plugin.yml") {
            expand("version" to project.version)
        }
    }
}
