plugins {
    alias(libs.plugins.runpaper)
}

dependencies {
    implementation(projects.core)
    implementation(libs.configurate)
    compileOnly(libs.paper)
    compileOnly(libs.cloud.paper)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    runServer {
        minecraftVersion(libs.versions.paper.get().substringBefore('-'))
    }
    processResources {
        filesMatching("paper-plugin.yml") {
            expand("version" to project.version)
        }
    }
    shadowJar {
        relocate("org.spongepowered", "me.xneox.epicguard.libs.configurate")
        relocate("com.typesafe", "me.xneox.epicguard.libs.typesafe")
        relocate("io.leangen.geantyref", "me.xneox.epicguard.libs.geantyref")
    }
}
