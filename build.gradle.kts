// -------------------- 루트 프로젝트에만 적용하는 설정: 시작 --------------------

/**
 * plugins {} 구문은 Gradle 2.1 이후 도입되었으며, 빌드 스크립트(build.gradle.kts)의 최상단에서 사용해야하고 플러그인 의존성 관리에 효율적이다.
 * 반면 apply {} 구문은 더 전통적인 방식으로 스크립트내 어느 곳에서나 사용할 수 있으며, 조건부로 플러그인을 적용하는 경우 유용하다.
 *
 */
plugins {
    // 참고) kotlin("aaa.bbb") 는 id("org.jetbrains.kotlin.aaa.bbb") 와 같다.

    /**
     * Kotlin 코드를 JVM 용 바이트 코드로 컴파일하는 Gradle 프로젝트에 사용된다.
     * 이 플러그인은 Kotlin 프로젝트가 JVM 환경에서 실행될 수 있도록 해주며, Kotlin 과 Java 언어를 함께 사용하는 프로젝트에서도 중요한 역할을 한다.
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
     * @see Kotlin 공식 문서(kapt): https://kotlinlang.org/docs/kapt.html
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.jetbrains.kotlin.kapt
     */
    kotlin("kapt")

    /**
     * Kotlin 에서 스프링 프레임워크와 호환되도록 클래스에 자동으로 open 수정자를 추가하는 컴파일러 플러그인이다.
     * 아래와 같은 스프링 어노테이션이 붙은 클래스들에 자동으로 open 처리가 된다.
     * 스프링 프로젝트에선 사실상 이 플러그인은 필수로 써야할 것 같다.
     * - '@Component', '@Async', '@Transactional', '@Cacheable', '@SpringBootTest'
     * - '@Configuration', '@Controller', '@RestController', '@Service', '@Repository' 등등
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.spring
     */
    kotlin("plugin.spring")

    /**
     * build.gradle.kts 에 정의한 어노테이션이 붙은 클래스를 open 처리하고 싶을때 사용하는 컴파일러 플러그인이다.
     * 보통은 plugin.spring 을 통해 open 이 필요한 스프링 빈 관련 클래스들이 open 처리가 되기 때문에 이 플러그인은 필수는 아니다.
     *
     * @see 사용법: https://kotlinlang.org/docs/all-open-plugin.html#gradle
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.allopen
     */
    kotlin("plugin.allopen") apply false

    /**
     * 기본 생성자를 자동으로 생성해주는 역할을 하는 플러그인이다.
     * 코틀린에서는 클래스의 모든 프로퍼티가 초기화되어야 인스턴스를 생성할 수 있는데, 이 플러그인을 사용하면 별도의 초기값 없이도 기본 생성자를 통해
     * 인스턴스를 만들 수 있다. 주로 ORM 라이브러리(JPA/Hibernate)나 직렬화 라이브러리(Jackson) 같이 기본 생성자를 필요로 하는 라이브러리를
     * 코틀린과 함께 사용할 때 유용하다.
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.noarg
     */
    kotlin("plugin.noarg") apply false

    /**
     * plugin.noarg 플러그인의 특수한 경우로 볼 수 있는데, JPA 사양을 사용하는 코틀린 엔티티에 매개변수 없는 생성자를 자동으로 추가한다.
     * 이 플러그인은 JPA 가 엔티티 클래스에 기본 생성자를 요구하는 것을 해결하기 위해 특별히 설계됐는데, 그냥 noarg 를 쓰면 모든 클래스의 기본 생성자가
     * 생기므로 noarg 를 쓰는게 일반적으로는 좋을 것 같다.
     * 만약 jpa 관련 엔티티 클래스만 noarg 로 쓰고자 하면 noarg 말고 jpa 를 쓰면 될 것 같다.
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.jetbrains.kotlin.plugin.jpa
     */
    kotlin("plugin.jpa") apply false

    /**
     * Spring Boot 애플리케이션을 위한 Gradle 플러그인이다.
     *  1. 애플리케이션 실행: bootRun 태스크를 통해 Spring Boot 애플리케이션을 직접 실행할 수 있다.
     *  2. 실행 가능한 JAR 생성: Spring Boot 애플리케이션은 종종 "uber-jar" 또는 "fat-jar" 로 패키징되는데, 이는 애플리케이션과 모든 종속성이
     *     하나의 큰 JAR 파일 안에 포함되어 배포됨을 의미한다. bootJar 작업은 이러한 실행 가능한 JAR 파일을 생성한다.
     *  3. 스타터 종속성 관리: Spring Boot 의 스타터 종속성들을 쉽게 관리할 수 있게 해줍니다. 스타터 종속성은 특정 기능을 구현하는데 필요한 모든
     *     라이브러리와 자동 설정 코드를 묶어놓은 것으로, 이를 통해 필요한 종속성을 일일이 찾아 추가하는 수고를 덜 수 있습니다.
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.springframework.boot
     */
    id("org.springframework.boot")

    /**
     * Spring Boot 와 같은 Spring 프로젝트의 종속성 관리를 용이하게 해주는 플러그인이다.
     * org.springframework.boot 플러그인 버전에 맞춰 관련된 스프링 버전들을 권장된 버전으로 세팅해준다.
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/io.spring.dependency-management
     */
    id("io.spring.dependency-management")

    /**
     * Gradle 기반의 코틀린 프로젝트에서 코드 스타일 및 포맷팅을 검사하고, 필요한 경우 자동으로 수정하는 기능을 제공하는 플러그인이다.
     * 이 플러그인은 ktlint, 코틀린용 인기 있는 코드 스타일 검사 도구를 사용한다.
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.jlleitschuh.gradle.ktlint
     */
    id("org.jlleitschuh.gradle.ktlint")

    /**
     * Gradle을 사용하는 프로젝트에서 Docker 이미지를 쉽게 빌드하고 관리할 수 있도록 해주는 Gradle 플러그이다.
     * 애플리케이션을 컨테이너화하는 과정을 간소화하여, 개발자가 Docker 이미지를 생성하고 배포하는 작업을 더욱 효율적으로 수행할 수 있게 돕는다.
     * 애플리케이션 모듈에서만 필요하므로 하위 애플리케이션 모듈에서만 사용하자. (하위 build.gradle.kts 에 plugins {} 에 추가)
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/com.palantir.docker
     */
    id("com.palantir.docker") apply false

    /**
     * Gradle 프로젝트에서 Node.js 와 npm 태스크를 통합하고 관리할 수 있도록 하는 플러그인이다.
     * Node.js 환경을 설정하고, npm 패키지를 설치하며 Node.js 나 npm 을 사용하는 다양한 작업을 자동화할 수 있다.
     * 예를 들어, 프론트엔드 자산을 빌드하거나 Node.js 스크립트를 실행하는 등의 작업이 포함될 수 있다.
     * 프론트엔드 모듈에서 필요하다면 등록해서 사용하자. (하위 build.gradle.kts 에 plugins {} 에 추가)
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/com.github.node-gradle.node
     * @see Github: https://github.com/node-gradle/gradle-node-plugin
     */
    id("com.github.node-gradle.node") apply false
}

/**
 * 인텔리제이 IDEA 관련된 Gradle 기능을 활성화하는 플러그인으로, 루트 프로젝트에만 적용하면 된다.
 *
 * @see Gradle 가이드: https://docs.gradle.org/current/userguide/idea_plugin.html
 */
apply(plugin = "idea")
apply(from = "${rootProject.projectDir}/gradle/project-dependency-rules.build.gradle.kts")
// -------------------- 루트 프로젝트에만 적용하는 설정: 끝 --------------------

// 모든 프로젝트에 적용되는 설정
allprojects {
    group = "sunset"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }

    apply {
        plugin("org.jetbrains.kotlin.jvm") // plugin("kotlin") 와 같다.
    }

    /**
     * Gradle 빌드시 사용할 JDK 의 버전을 지정한다.
     * 이는 실제로 컴파일러와 런타임 환경이 사용하는 JDK 의 버전을 지정하는 것으로, Kotlin 컴파일러 뿐만 아니라 Java 소스 파일을 컴파일하거나 프로젝트를
     * 실행할 때 사용되는 JDK 버전에도 영향을 준다.
     *
     * @since Gradle 6.7 이상부터 도입된 설정
     * @see Kotlin Blog - Gradle JVM 툴체인 지원: https://docs.gradle.org/current/userguide/toolchains.html
     * @see Gradle 사용자 메뉴얼 - JVM 프로젝트를 위한 툴체인: https://blog.jetbrains.com/kotlin/2021/11/gradle-jvm-toolchain-support-in-the-kotlin-plugin/
     */
    kotlin {
        jvmToolchain(17)
    }

    // 라이브러리 버전등의 변수를 정의
    ext {
        // set("xxxVersion", "x.y.z")
    }
}

// 코드가 작성되는 leaf 하위 프로젝트에 적용되는 설정
configure(subprojects.filter { it.subprojects.isEmpty() }) {
    apply {
        plugin("org.jetbrains.kotlin.jvm") // plugin("kotlin") 와 같다.
        plugin("org.jetbrains.kotlin.kapt")
        plugin("org.jetbrains.kotlin.plugin.spring") // plugin("kotlin-spring") 와 같다
        plugin("org.springframework.boot")
        plugin("io.spring.dependency-management")
        plugin("org.jlleitschuh.gradle.ktlint")
    }

    tasks {
        /**
         * Kotlin 컴파일 태스크 관련 옵션을 설정한다.
         */
        withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
            kotlinOptions {
                // Kotlin 컴파일러에 추가적인 인자들을 전달한다.
                // - JSR-305 어노테이션을 엄격하게 처리하는 인자. 이는 널 가능성 어노테이션이 붙은 자바 코드를 코틀린에서 사용할 때, 널 안정성 체크를 강화한다.
                //   javax.annotation.Nullable(@Nullable) 이 붙은게 있다면 코틀린에서 널가능성 타입으로 처리한다.
                //   javax.annotation.Nonnull(@NotNull) 이 붙은게 있다면 코틀린에서 널이 아닌 타입으로 처리한다.
                freeCompilerArgs = listOf("-Xjsr305=strict")
                // 생성되는 코틀린 바이트코드가 호환되어야 하는 JVM 타겟 버전을 지정한다.
                jvmTarget = "17"
                // 사용할 코틀린 언어 버전을 지정한다.
                // 버전 번호는 'x.x' 형식을 따른다. 예를 들면 1.9.23 을 쓴다면 1.9 로 작성한다.
                languageVersion = "1.9"
            }
        }

        withType<Test>().configureEach {
            useJUnitPlatform()
            // TODO: 확인
            maxParallelForks = (Runtime.getRuntime().availableProcessors() / 2).takeIf { it > 0 } ?: 1
        }
    }

    // TODO: sourceSets 설정

    // TODO: integrationTest 설정

    // TODO: 확인
    dependencies {
        /**
         * Kotlin 코드에서 Java 8의 추가 기능(예: java.util.stream, java.time API 등)을 활용할 수 있는 Kotlin 표준 라이브러리의 확장 기능에 접근할 수 있다.
         * 그래서 Java 8 특화 기능을 Kotlin에서 더 편리하게 사용할 수 있는 이점을 누릴 수 있다.
         *
         * 참고) 코틀린 컴파일러의 Java 버전과는 큰 상관이 없다. Java 17 로 컴파일되더라도 stdlib-jdk8 을 추가하면 확장 기능을 통해 더 편리하게 jdk8 내용을 사용할 수 있다.
         */
        implementation(kotlin("stdlib-jdk8"))

        /**
         * Kotlin 리플렉션 API에 접근할 수 있게된다. 리플렉션을 이용해 실행 중에 클래스, 함수, 프로퍼티의 메타데이터를 검사하고 조작할 수 있다.
         * 이 의존성 없이는 고급 리플렉션 기능에 접근할 수 없으며, 코드에서 런타임에 타입 정보를 사용하는 데 제한이 생긴다.
         */
        implementation(kotlin("reflect"))

        /**
         *  Kotlin 표준 라이브러리의 테스트 모듈을 프로젝트의 테스트 컴파일 클래스패스에 추가하는 것이다.
         *  이를 통해 Kotlin 코드를 테스트할 때 필요한 기능들, 예를 들어 JUnit과 함께 사용할 수 있는 어서션(assertion) 함수들을 사용할 수 있게 된다.
         *  Kotlin 테스트 라이브러리는 테스트 코드 작성을 보다 간편하게 하고, Kotlin 특유의 기능들을 테스트에 활용할 수 있게 돕는다.
         */
        testImplementation(kotlin("test"))

        implementation("org.springframework.boot:spring-boot-starter")

        /**
         * Spring Boot 애플리케이션을 위한 테스트 스타터 패키지이다.
         * JUnit, Spring Test, Spring Boot Test, AssertJ, Hamcrest, Mockito, JSONassert 및 JsonPath와 같은
         * 여러 유명한 테스팅 라이브러리가 포함되어 있어, 스프링 부트 애플리케이션을 테스트하는 데 필요한 대부분의 도구를 제공한다.
         */
        testImplementation("org.springframework.boot:spring-boot-starter-test") {
            // junit4 기반인 모듈은 제외한다.
            exclude(module = "junit")
            // junit5 의 일부인데, junit4 기반의 테스트를 junit5 환경에서 실행할 수 있게 해주는 엔진이다. 레거시가 아니라면 필요없으므로 제외한다.
            exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
        }
    }
}

// 애플리케이션 하위 프로젝트에 적용되는 설정
configure(subprojects.filter { it.subprojects.isEmpty() }.filter { it.path.contains(":app") }) {
    apply {
        plugin("com.palantir.docker")
    }
    tasks {
        bootJar { enabled = true }
        jar { enabled = false }
    }
}

// 라이브러리 하위 프로젝트에 적용되는 설정
configure(subprojects.filter { it.subprojects.isEmpty() }.filter { !it.path.contains(":app") }) {
    tasks {
        bootJar { enabled = false }
        jar { enabled = true }
    }
}
