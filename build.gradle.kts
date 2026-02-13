plugins {
    java
    application
    id("org.springframework.boot") version "3.5.9"
    id("io.spring.dependency-management") version "1.1.7"
}

repositories {
    mavenCentral()
}

application {
    mainClass = "org.beyene.camel.Application"
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(arrayOf("-Xlint:all"))
    options.encoding = "UTF-8"
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}

dependencies {
    val camelVersion = "4.17.0"

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.camel.springboot:camel-spring-boot-starter:$camelVersion")
    // Note: camel-zeromq from camel-extra is not compatible with Camel 4.x
    // The project would need to find an alternative ZeroMQ integration or
    // wait for camel-extra to release a Camel 4.x compatible version
    // implementation("org.apache-extras.camel-extra:camel-zeromq:2.25.2") {
    //     exclude(group = "org.zeromq", module = "zeromq-scala-binding_2.10")
    //     exclude(group = "com.typesafe.akka", module = "akka-zeromq_2.10")
    //     exclude(group = "com.typesafe.akka", module = "akka-actor_2.10")
    // }
    implementation("org.zeromq:jeromq:0.6.0")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.apache.camel:camel-core:$camelVersion")
}