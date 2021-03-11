plugins {
    kotlin("jvm") version "1.4.10"
}

group = "com.nicolettetovstashy"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.processing:core:3.3.7")
    implementation("org.processing:serial:3.3.7")
    implementation("io.github.java-native:jssc:2.9.2")
    implementation("io.ktor:ktor-client:1.5.1")
    implementation("io.ktor:ktor-client-cio:1.5.1")
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
    compileTestKotlin {
        kotlinOptions.jvmTarget = "1.8"
    }
}