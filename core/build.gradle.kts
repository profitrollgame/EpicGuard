plugins {
    alias(libs.plugins.blossom)
    `maven-publish`
    signing
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
    replaceToken("{version}", project.version, "src/main/java/me/xneox/epicguard/core/util/VersionUtils.java")
}

// Publish to Maven Central
/*publishing {
    publications {
        create<MavenPublication>("maven") {
            repositories {
                maven {
                    credentials(PasswordCredentials::class)
                    val central = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                    val snapshots = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                    if (project.version.toString().endsWith("SNAPSHOT")) {
                        name = "SonatypeSnapshots"
                        setUrl(snapshots)
                    } else {
                        name = "OSSRH"
                        setUrl(central)
                    }
                }
            }
            from(components["java"])
            pom {
                url.set("https://github.com/4drian3d/EpicGuard")
                licenses {
                    license {
                        name.set("GNU General Public License version 3 or later")
                        url.set("https://opensource.org/licenses/GPL-3.0")
                    }
                }
                scm {
                    connection.set("scm:git:https://github.com/4drian3d/EpicGuard.git")
                    developerConnection.set("scm:git:ssh://git@github.com/4drian3d/EpicGuard.git")
                    url.set("https://github.com/4drian3d/EpicGuard")
                }
                developers {
                    developer {
                        id.set("4drian3d")
                        name.set("Adrian Gonzales")
                        email.set("adriangonzalesval@gmail.com")
                    }
                    developer {
                        id.set("awumii")
                        email.set("awumii@protonmail.com")
                    }
                }
                issueManagement {
                    name.set("GitHub")
                    url.set("https://github.com/4drian3d/EpicGuard/issues")
                }
                ciManagement {
                    name.set("GitHub Actions")
                    url.set("https://github.com/4drian3d/EpicGuard/actions")
                }
                name.set(project.name)
                description.set(project.description)
                url.set("https://github.com/4drian3d/EpicGuard")
            }
            artifactId = "epicguard-api"
        }
    }
}
signing {
    useGpgCmd()
    sign(configurations.archives.get())
    sign(publishing.publications["maven"])
}*/