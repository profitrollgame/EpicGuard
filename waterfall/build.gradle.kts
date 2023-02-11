plugins {
    alias(libs.plugins.runwaterfall)
}

dependencies {
    implementation(projects.core)

    compileOnly(libs.adventure.platform.bungeecord)
    compileOnly(libs.waterfall)
}

tasks {
    build {
        dependsOn(shadowJar)
    }
    runWaterfall {
        waterfallVersion(libs.versions.waterfall.get().substringBefore('-'))
    }
}
