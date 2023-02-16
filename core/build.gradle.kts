plugins {
    `maven-publish`
    alias(libs.plugins.blossom)
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

blossom {
    replaceToken("{version}", project.version, "src/main/java/me/xneox/epicguard/core/util/VersionUtils.java")
}

// Publish to jitpack.org
publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "EpicGuard"

            from(components["java"])
        }
    }
}
