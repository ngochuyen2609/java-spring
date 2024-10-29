group = "ngoc.huyen.it"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

plugins {
    java
    application
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
    id("io.freefair.lombok") version "8.10.2" // Đảm bảo đã thêm plugin Lombok
}
java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17) // Thay 17 bằng phiên bản bạn đang sử dụng
    }
}
configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

dependencies {
    implementation(platform("org.springframework.boot:spring-boot-dependencies:3.3.5")) // Thêm platform cho Spring Boot
    implementation("org.springframework.boot:spring-boot-starter-web")
    compileOnly("org.projectlombok:lombok") // Sử dụng lombok
    annotationProcessor("org.projectlombok:lombok") // Thêm annotation processor cho lombok
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}
// Cấu hình chạy thử nghiệm
val mainClassName = "work.ngochuyen.spring.Application"
tasks.withType<JavaExec> {
    mainClass.set(mainClassName)
}
tasks.withType<Test> {
    useJUnitPlatform() // Sử dụng JUnit Platform
}