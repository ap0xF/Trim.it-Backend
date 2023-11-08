import com.google.protobuf.gradle.*

plugins {
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("io.micronaut.application") version "4.0.4"
    id("com.google.protobuf") version "0.9.2"
    id("io.micronaut.test-resources") version "4.0.4"
}

version = "0.1"
group = "com.syano"

repositories {
    mavenCentral()
}

dependencies {
    annotationProcessor("io.micronaut.data:micronaut-data-processor")
//    annotationProcessor("io.micronaut.security:micronaut-security-annotations")
    annotationProcessor("io.micronaut.serde:micronaut-serde-processor")
    implementation("io.micronaut:micronaut-aop")
    implementation("io.micronaut:micronaut-discovery-core")
    implementation("io.micronaut.data:micronaut-data-hibernate-jpa")
    implementation("io.micronaut.grpc:micronaut-grpc-runtime")
//    implementation("io.micronaut.security:micronaut-security-jwt")
    implementation("io.micronaut.serde:micronaut-serde-jackson")
    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("javax.annotation:javax.annotation-api")
    runtimeOnly("ch.qos.logback:logback-classic")
    // for mongodb connection
    annotationProcessor("io.micronaut.data:micronaut-data-document-processor")
    implementation("io.micronaut.data:micronaut-data-mongodb")
    runtimeOnly("org.mongodb:mongodb-driver-sync")

    // third-party depndency for base62 encoding
    implementation("io.seruco.encoding:base62:0.1.3")


}


application {
    mainClass.set("com.syano.Application")
}
java {
    sourceCompatibility = JavaVersion.toVersion("17")
    targetCompatibility = JavaVersion.toVersion("17")
}

sourceSets {
    main {
        java {
            srcDirs("build/generated/source/proto/main/grpc")
            srcDirs("build/generated/source/proto/main/java")
        }
    }
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.23.4"
    }
    plugins {
        id("grpc") {
            artifact = "io.grpc:protoc-gen-grpc-java:1.56.1"
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
micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.syano.*")
    }


}



