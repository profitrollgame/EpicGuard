plugins {
    alias(libs.plugins.runpaper)
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
