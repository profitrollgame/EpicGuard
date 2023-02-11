plugins {
    alias(libs.plugins.runvelocity)
}

dependencies {
    implementation(projects.core)
    implementation(libs.libby.velocity)

    compileOnly(libs.velocity)
    annotationProcessor(libs.velocity)
}

tasks {
    shadowJar {
        relocate("net.byteflux.libby", "me.xneox.epicguard.velocity.libby")
    }
    build {
        dependsOn(shadowJar)
    }
    runVelocity {
        velocityVersion(libs.versions.velocity.get())
    }
}
