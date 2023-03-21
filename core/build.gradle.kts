plugins {
    alias(libs.plugins.blossom)
    alias(libs.plugins.indra)
}

dependencies {
    implementation(libs.geoip)
    implementation(libs.commons.compress)
    implementation(libs.commons.text)
    compileOnly(libs.guava)
    compileOnly(libs.caffeine)
    implementation(libs.configurate)
    implementation(libs.hikaricp) {
        exclude(group = libs.slf4j.get().group)
    }

    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.serializer.legacy)
    compileOnly(libs.log4j2)
    compileOnly(libs.slf4j)

    compileOnly(libs.annotations)
}

tasks {
    javadoc {
        options {
            this.outputLevel = JavadocOutputLevel.QUIET
        }
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

blossom {
    replaceTokenIn("src/main/java/me/xneox/epicguard/core/util/VersionUtils.java")
    replaceToken("{version}", project.version)
    replaceToken("{hikari}", libs.versions.hikaricp)
    replaceToken("{configurate}", libs.versions.configurate)

}

// Publish to Maven Central
indra {
    javaVersions {
        testWith().add(11)
    }
    github("4drian3d", "EpicGuard") {
        ci(true)
    }
    publishReleasesTo("OSSRH", "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
    publishSnapshotsTo("SonatypeSnapshots",  "https://s01.oss.sonatype.org/content/repositories/snapshots/")
    gpl3OrLaterLicense()
    configurePublications {
        artifactId = "epicguard-api"
        from(components["java"])

        pom {
            developers {
                developer {
                    id.set("4drian3d")
                    name.set("Adrian Gonzales")
                    email.set("adriangonzalesval@gmail.com")
                }
            }
        }
    }
}