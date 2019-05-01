//buildscript {
//    repositories {
//        jcenter()
//    }
//    dependencies {
//        classpath(kotlin("edu.sc.seis.gradle:launch4j", version = "2.4.6"))
//    }
//}
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
plugins {
    kotlin("jvm") version "1.3.30"
    id("edu.sc.seis.launch4j") version "2.4.6"
    //application
}

group = "com.azedevs"
version = "1.0"

repositories {
    jcenter()
    mavenCentral()
    maven("https://oss.jfrog.org/artifactory/libs-release")
    maven("https://dl.bintray.com/kotlin/kotlin-dev")
    maven("https://oss.sonatype.org/content/repositories/snapshots/")
    maven("https://jitpack.io")
}

val lwjglVersion = "3.2.1"
val lwjglNatives = "natives-windows"

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.2.1")
    implementation("com.github.kotlin-graphics:imgui:v1.68.01-00") //https://github.com/kotlin-graphics/imgui
    implementation("org.jire.kotmem:Kotmem:0.86")
    implementation("net.java.dev.jna:jna:5.3.1")
    implementation("net.java.dev.jna:jna-platform:5.3.1")
    implementation("edu.sc.seis.gradle:launch4j:2.4.6")

    // Database
    implementation("org.jdbi:jdbi3-sqlobject:3.8.0") // http://jdbi.org/#_introduction_to_jdbi
    implementation("org.jdbi:jdbi3-postgres:3.8.0")
    implementation("org.jdbi:jdbi3-kotlin-sqlobject:3.8.0")
    implementation("org.postgresql:postgresql:42.2.5")
    implementation("org.jdbi:jdbi3-core:3.8.0")
    implementation("org.slf4j:slf4j-nop:1.8.0-beta4")

    // Twitch
    implementation("com.github.twitch4j:twitch4j:1.0.0-alpha.13")

    // GUI
    implementation("com.github.kotlin-graphics:uno-sdk:0.7.6")
    implementation("com.github.kotlin-graphics:glm:v1.0.1")
    implementation("com.github.kotlin-graphics:imgui:v1.68.01-00")
    implementation("org.lwjgl", "lwjgl", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-assimp", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-bgfx", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-cuda", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-egl", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-glfw", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-jawt", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-jemalloc", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-libdivide", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-llvm", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-lmdb", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-lz4", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-meow", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-nanovg", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-nfd", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-nuklear", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-odbc", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-openal", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-opencl", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-opengl", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-opengles", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-openvr", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-opus", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-ovr", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-par", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-remotery", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-rpmalloc", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-sse", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-stb", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-tinyexr", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-tinyfd", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-tootle", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-vma", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-vulkan", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-xxhash", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-yoga", lwjglVersion)
    implementation("org.lwjgl", "lwjgl-zstd", lwjglVersion)
    runtimeOnly("org.lwjgl", "lwjgl", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-assimp", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-bgfx", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-glfw", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-jemalloc", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-libdivide", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-llvm", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-lmdb", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-lz4", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-meow", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nanovg", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nfd", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-nuklear", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openal", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengl", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opengles", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-openvr", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-opus", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-ovr", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-par", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-remotery", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-rpmalloc", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-sse", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-stb", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-tinyexr", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-tinyfd", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-tootle", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-vma", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-xxhash", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-yoga", lwjglVersion, classifier = lwjglNatives)
    runtimeOnly("org.lwjgl", "lwjgl-zstd", lwjglVersion, classifier = lwjglNatives)
}

launch4j {
    mainClassName = "MainKt"
    icon = "${projectDir}/resources/ic_gearnet.ico"
}

val fatJar = task("fatJar", type = Jar::class) {
    baseName = "${project.name}-fat"
    manifest {
        attributes["Implementation-Title"] = "GearNet Xrd"
        attributes["Implementation-Version"] = version
        attributes["Main-Class"] = "Main"
    }
    from(configurations.runtime.get().map({ if (it.isDirectory) it else zipTree(it) })) //delete .get() if your gradle is less than v5
    with(tasks["jar"] as CopySpec)
}



tasks.withType<KotlinCompile> {
    kotlinOptions.freeCompilerArgs += "-Xuse-experimental=kotlin.Experimental"
}

tasks {
    "build" {
        dependsOn(fatJar)
    }
}
