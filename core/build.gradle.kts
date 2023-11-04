plugins {
    alias(libs.plugins.idea.ext)
    alias(libs.plugins.blossom)
    `maven-publish`
    signing
}

dependencies {
    compileOnly(libs.geoip)
    compileOnly(libs.commons.compress)
    compileOnly(libs.commons.text)
    compileOnly(libs.guava)
    compileOnly(libs.caffeine)
    compileOnly(libs.configurate)
    compileOnly(libs.hikaricp)

    compileOnly(libs.adventure.api)
    compileOnly(libs.adventure.minimessage)
    compileOnly(libs.adventure.serializer.legacy)
    compileOnly(libs.log4j2)
    compileOnly(libs.miniplaceholders)

    compileOnly(libs.annotations)
    compileOnly(libs.fuzzywuzzy)
    compileOnly(libs.cloud.core)
}

tasks {
    javadoc {
        options {
            this.outputLevel = JavadocOutputLevel.QUIET
            encoding = Charsets.UTF_8.name()
        }
    }
    compileJava {
        options.encoding = Charsets.UTF_8.name()
        options.release.set(17)
    }
}

java {
    withSourcesJar()
    withJavadocJar()
}

sourceSets {
    main {
        blossom {
            javaSources {
                property("version", project.version.toString())
                property("hikari", libs.versions.hikaricp.get())
                property("configurate", libs.versions.configurate.get())
                property("caffeine", libs.versions.caffeine.get())
                property("compress", libs.versions.commons.compress.get())
                property("text", libs.versions.commons.text.get())
                property("geoip", libs.versions.geoip.get())
                property("jackson", libs.versions.jackson.get())
                property("maxminddb", libs.versions.maxmind.db.get())
                property("fuzzywuzzy", libs.versions.fuzzywuzzy.get())
                property("cloud", libs.versions.cloud.get())
            }
        }
    }
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
