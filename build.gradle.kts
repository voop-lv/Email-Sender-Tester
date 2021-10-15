plugins {
    java
    `java-library`
    `maven-publish`
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

apply(plugin = "com.github.johnrengelman.shadow")

tasks.compileJava {
    options.encoding = "UTF-8"
}

tasks.jar {
    archiveBaseName.set("EmailSenderTester")
    archiveClassifier.set("")
    archiveVersion.set("")
}
tasks.shadowJar{
    archiveBaseName.set("EmailSenderTester")
    archiveClassifier.set("")
    archiveVersion.set("")
}

tasks.build {
    dependsOn("shadowJar")
}
tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = "lv.voop.open.tools.emailsendertester.EmailSenderTester"
    }
}

group = "lv.voop.open.tools"
description = "EmailSenderTester"
version = "1.0"
java.sourceCompatibility = JavaVersion.VERSION_17
java.targetCompatibility = JavaVersion.VERSION_17

//Dependencies Version
val lombokVersion = "1.18.22"
val nightConfigVersion = "3.6.3"
val javaxMailVer = "1.6.2"

repositories {
    mavenCentral()
}
dependencies {
    compileOnly("org.projectlombok:lombok:$lombokVersion")
    annotationProcessor("org.projectlombok:lombok:$lombokVersion")
    api("com.electronwill.night-config:core:$nightConfigVersion")
    api("com.electronwill.night-config:toml:$nightConfigVersion")
    api("com.sun.mail:javax.mail:$javaxMailVer")
}