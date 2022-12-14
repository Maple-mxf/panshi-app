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
        }
    }
}

subprojects {
    apply(plugin = "base")
    apply(plugin = "java")
    apply(plugin = "idea")
    apply(plugin = "com.google.protobuf")

    dependencies {
        implementation("org.projectlombok:lombok:1.18.16")
        implementation("com.google.guava:guava:31.1-jre")
        implementation("io.grpc:grpc-netty-shaded:1.48.0")
        implementation("io.grpc:grpc-protobuf:1.48.0")
        implementation("io.grpc:grpc-stub:1.48.0")
        implementation("io.grpc:grpc-api:1.48.0")
        implementation("io.grpc:grpc-services:1.48.0")
        implementation("io.grpc:grpc-core:1.48.0")
        implementation("io.grpc:grpc-context:1.48.0")

        annotationProcessor("org.projectlombok:lombok:1.18.16")
        testImplementation("junit", "junit", "4.12")
    }
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

project("config-provider") {
    group = "io.panshi.config.provider"
    version = "1.0"
    dependencies {
        implementation(project(":protocol"))
        implementation(project(":grpc-discovery"))
        implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.0")
        implementation("io.etcd:jetcd-core:0.7.3")
    }
}

project("config-consumer") {
    group = "io.panshi.config.consumer"
    version = "1.0"
    dependencies {
        implementation(project(":protocol"))
        implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.0")
    }
}

project("discovery-core") {
    group = "io.panshi.discovery.core"
    version = "1.0"
    dependencies {
        implementation("io.etcd:jetcd-core:0.7.3")
        implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.0")
        implementation("org.mapstruct:mapstruct:1.5.3.Final")
        annotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Fina")
        testAnnotationProcessor("org.mapstruct:mapstruct-processor:1.5.3.Fina")
    }
}

project("grpc-discovery") {
    group = "io.panshi.grpc.discovery"
    version = "1.0"
    dependencies {
        implementation(project(":discovery-core"))
        implementation("io.etcd:jetcd-core:0.7.3")
        implementation("com.fasterxml.jackson.core:jackson-core:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
        implementation("com.fasterxml.jackson.core:jackson-databind:2.13.0")
        implementation("com.fasterxml.jackson.dataformat:jackson-dataformat-yaml:2.13.0")
    }
}

project("protocol") {
    group = "io.panshi.protocol"
    version = "1.0"
    dependencies {}
    protobuf {
        generatedFilesBaseDir = "$projectDir/src"
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
                    id("grpc")
                }
            }
        }
    }
    idea {
        module {
            generatedSourceDirs.addAll(listOf(
                    file("${protobuf.protobuf.generatedFilesBaseDir}/main/grpc"),
                    file("${protobuf.protobuf.generatedFilesBaseDir}/main/java")
            ))
        }
    }
    sourceSets {
        main {
            proto {
                srcDir("src/main/proto")
            }
        }
        getByName("main").java.srcDirs("src/main/grpc")
        getByName("main").java.srcDirs("src/main/java")
    }
}