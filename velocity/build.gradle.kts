import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

dependencies {
    implementation(project(":core"))

    compileOnly("mysql:mysql-connector-java:8.0.27")
    compileOnly("org.xerial:sqlite-jdbc:3.36.0.3")
    implementation("net.byteflux:libby-velocity:1.1.5")

    compileOnly("com.velocitypowered:velocity-api:3.1.2-SNAPSHOT")
    annotationProcessor("com.velocitypowered:velocity-api:3.1.2-SNAPSHOT")
}

tasks.withType<ShadowJar> {
    relocate("net.byteflux.libby", "me.xneox.epicguard.velocity.libby")
}

tasks {
    build {
        dependsOn(shadowJar)
    }
}
