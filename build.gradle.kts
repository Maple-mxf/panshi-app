import com.google.protobuf.gradle.*

plugins {
    id("base")
    id("idea")
    id("java")
    id("com.google.protobuf") version "0.8.18"
}

java.sourceCompatibility = JavaVersion.VERSION_11
java.targetCompatibility = JavaVersion.VERSION_11

group = "io.panshi"
version = "1.0-SNAPSHOT"

buildscript {
    repositories {
        mavenCentral()
        maven { url = uri("https://maven.aliyun.com/nexus/content/groups/public") }
        maven { url = uri("https://plugins.gradle.org/m2/") }
        gradlePluginPortal()
    }
}



repositories {
    mavenCentral()
}

allprojects {
    repositories {
        mavenCentral()
        maven {
            setUrl("https://maven.aliyun.com/nexus/content/groups/public")
            setUrl("https://repo.spring.io/release")
            setUrl("https://repo.spring.io/milestone")
        }
    }
}

subprojects {
    apply(plugin = "base")
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "com.google.protobuf")

    protobuf {
        protoc {
            artifact = "com.google.protobuf:protoc:3.6.1"
        }
        plugins {
            id("grpc") {
                artifact = "io.grpc:protoc-gen-grpc-java:1.20.0"
            }
        }
        generateProtoTasks {
            ofSourceSet("main").forEach {
                it.plugins {
                    // Apply the "grpc" plugin whose spec is defined above, without options.
                    id("grpc")
                }
            }
        }
    }

    dependencies {
        implementation("org.projectlombok:lombok:1.18.16")
        implementation("com.google.guava:guava:31.1-jre")

        implementation("io.grpc:grpc-netty-shaded:1.20.0")
        implementation("io.grpc:grpc-protobuf:1.20.0")
        implementation("io.grpc:grpc-stub:1.20.0")

        annotationProcessor("org.projectlombok:lombok:1.18.16")
        testImplementation("junit", "junit", "4.12")
    }

    idea {
        module {
            generatedSourceDirs.addAll(listOf(
                file("${protobuf.protobuf.generatedFilesBaseDir}/main/grpc"),
                file("${protobuf.protobuf.generatedFilesBaseDir}/main/java")
            ))
        }
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

project("panshi-config-server") {
    group = "com.suiteopen.config.server"
    version = "1.0"

    dependencies {

        implementation(project(":panshi-proto"))

        implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
        // https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")

    }
}

project("panshi-config-client") {
    group = "com.suiteopen.config.client"
    version = "1.0"
    dependencies {}
}

project("panshi-grpc-etcd") {
    group = "com.suiteopen.grpc.etcd"
    version = "1.0"
    dependencies {}
}

project("panshi-console-server") {
    group = "com.suiteopen.console.server"
    version = "1.0"
    dependencies {
        implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
    }
}