import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.3.30"
    id("edu.sc.seis.launch4j") version "2.4.6"
}

group = "com.azedevs"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://oss.jfrog.org/artifactory/libs-release")
    maven("https://dl.bintray.com/kotlin/kotlin-dev")
    maven("https://jitpack.io")
}

val lwjglVersion = "3.2.2-SNAPSHOT"
val lwjglNatives = "natives-windows"

dependencies {
    // Core
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1")
    implementation("edu.sc.seis.gradle:launch4j:2.4.6")

    // Memscan
    implementation("org.jire.kotmem:Kotmem:0.86")

    // Twitch
    implementation("com.github.twitch4j:twitch4j:1.0.0-alpha.13")

    // Database
    implementation("org.jdbi:jdbi3-sqlobject:3.8.0")
    implementation("org.jdbi:jdbi3-postgres:3.8.0")
    implementation("org.jdbi:jdbi3-kotlin-sqlobject:3.8.0")
    implementation("org.jdbi:jdbi3-core:3.8.0")
    implementation("org.postgresql:postgresql:42.2.5")
    implementation("org.slf4j:slf4j-nop:1.8.0-beta4")

    // GUI
    implementation("no.tornado:tornadofx:1.7.17")
}

launch4j {
    mainClassName = "MainKt"
    icon = "${projectDir}/resources/ic_gearnet.ico"
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes("Implementation-Title" to "GearNet Xrd",
            "Implementation-Version" to version,
            "Main-Class" to "MainKt")
    }

    dependsOn(configurations.runtimeClasspath)
    from(configurations.runtimeClasspath.filter{ it.name.endsWith("jar") }.map{ zipTree(it) })
    with(tasks["jar"] as CopySpec)
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "10"
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}