plugins {
    java
    application
    id("org.springframework.boot") version "2.1.2.RELEASE"
    id("io.spring.dependency-management") version "1.0.6.RELEASE"
    `build-scan`
}

repositories {
    jcenter()
}

application {
    mainClassName = "org.beyene.camel.Application"
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<JavaCompile>().configureEach {
    options.compilerArgs.addAll(arrayOf("-Xlint:all"))
    options.encoding = "UTF-8"
}

dependencies {
    // 3.0.0-M1, see https://stackoverflow.com/q/55314360/1809463
    val camelVersion = "2.23.1"

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.apache.camel:camel-spring-boot-starter:$camelVersion")
    implementation("org.apache-extras.camel-extra:camel-zeromq:2.22.0") {
        exclude(group = "org.zeromq", module = "zeromq-scala-binding_2.10")
        exclude(group = "com.typesafe.akka", module = "akka-zeromq_2.10")
        exclude(group = "com.typesafe.akka", module = "akka-actor_2.10")
    }
    implementation("org.zeromq:jeromq:0.4.0")

    testImplementation("junit:junit:4.12")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.apache.camel:camel-test:$camelVersion")
    testImplementation("org.apache.camel:camel-test-spring:$camelVersion")
}

if (hasProperty("buildScan")) {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
    }
}