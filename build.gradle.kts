
plugins {
    /**
     * Kotlin 코드를 JVM 용 바이트 코드로 컴파일하는 Gradle 프로젝트에 사용된다.
     * 이 플러그인은 Kotlin 프로젝트가 JVM 환경에서 실행될 수 있도록 해주며, Kotlin 과 Java 언어를 함께 사용하는 프로젝트에서도 중요한 역할을 한다.
     *
     * 참고) kotlin("jvm") 과 id("org.jetbrains.kotlin.jvm") 은 같다.
     *
     * @see Kotlin 공식 문서(Gradle 과 Kotlin/JVM 시작하기): https://kotlinlang.org/docs/get-started-with-jvm-gradle-project.html
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.jetbrains.kotlin.jvm
     */
    kotlin("jvm")

    /**
     * Kotlin 에서 Java 주석 처리기를 사용할 수 있도록 해주는 컴파일러 플러그인이다.
     * Java 라이브러리를 전혀 쓰지 않는 코틀린 프로젝트라면 이 플러그인은 필요하지 않다.
     * 하지만 스프링을 사용하면, Java 기반의 주석 처리기(Annotation Processors)가 필요한 기능들이 보통 필요하게 되므로 거의 필수적으로 추가해야 한다.
     *
     * 참고) kotlin("kapt") 과 id("org.jetbrains.kotlin.kapt") 은 같다.
     *
     * @see Kotlin 공식 문서(kapt): https://kotlinlang.org/docs/kapt.html
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.jetbrains.kotlin.kapt
     */
    kotlin("kapt")


    id("org.springframework.boot") version "3.2.4"
    id("io.spring.dependency-management")
}

group = "sunset"
version = "1.0.0"

repositories {
    mavenCentral()
}

//apply {
//    plugin("idea")
//    plugin("kotlin")
//}

dependencies {
    testImplementation(kotlin("test"))

    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}
