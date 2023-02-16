plugins {
    alias(libs.plugins.runpaper)
    alias(libs.plugins.pluginyml.bukkit)
}

dependencies {
    implementation(projects.core)
    compileOnly(libs.paper)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    runServer {
        minecraftVersion(libs.versions.paper.get().substringBefore('-'))
    }
}

bukkit {
    main = "me.xneox.epicguard.paper.EpicGuardPaper"
    description = project.description as String
    name = "EpicGuard"
    version = project.version as String
    apiVersion = "1.17"
    authors = listOf("neox", "4drian3d")
    load = net.minecrell.pluginyml.bukkit.BukkitPluginDescription.PluginLoadOrder.STARTUP
    website = "https://github.com/4drian3d/EpicGuard"
    commands {
        register("epicguard") {
            description = "Main plugin command."
            permission = "epicguard.admin"
            aliases = listOf("guard", "epicguardpaper", "guardpaper")
        }
    }
    libraries = listOf(
        "${libs.caffeine.get().module}:${libs.versions.caffeine.get()}"
    )
}