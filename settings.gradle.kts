// 코틀린 공식문서 - Gradle 툴 설정 관련 설명: https://kotlinlang.org/docs/gradle.html

rootProject.name = "template-kotlin-spring"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    // @see 코를린 플러그인과 그레이들 호환 버전: https://kotlinlang.org/docs/gradle-configure-project.html#apply-the-plugin
    val pluginNamespaces =
        mapOf(
            "org.jetbrains.kotlin" to "1.9.23",
            "org.jetbrains.kotlin.plugin" to "1.9.23",
        )

    val pluginIds =
        mapOf(
            "org.springframework.boot" to "3.2.4",
            "io.spring.dependency-management" to "1.1.4",
            "org.jlleitschuh.gradle.ktlint" to "12.1.0",
            "com.palantir.docker" to "0.36.0",
            "com.github.node-gradle.node" to "7.0.2",
            "org.gradle.toolchains.foojay-resolver-convention" to "0.8.0"
        )

    resolutionStrategy {
        eachPlugin {
            if (pluginIds.containsKey(requested.id.id)) {
                println("plugin 버전: ${requested.id.id} >>> ${pluginIds[requested.id.id]}")
                useVersion(pluginIds[requested.id.id])
            } else if (pluginNamespaces.containsKey(requested.id.namespace)) {
                println("plugin 버전: ${requested.id.id} >>> ${pluginNamespaces[requested.id.namespace]}")
                useVersion(pluginNamespaces[requested.id.namespace])
            }
        }
    }
}

plugins {
    /**
     * Foojay Disco API를 활용하여 Gradle 프로젝트에서 필요한 Java 런타임을 자동으로 찾고 설정하는 기능을 제공한다.
     * 이 플러그인을 사용하면 개발자는 프로젝트에 적합한 Java 버전을 수동으로 찾고 설정하는 번거로움 없이, 효율적으로 Java 런타임 환경을 구성할 수 있다.
     * 이로 인해 Java 버전 관리가 간소화되고, 프로젝트의 설정 및 빌드 과정이 더욱 원활해진다.
     *
     * 하지만 모든 Gradle 프로젝트에 필수적인 것은 아닌데, 특히 여러 버전의 자바를 사용하는 복잡한 프로젝트에서 도움이 될 수 있다.
     * 프로젝트의 요구사항과 설정에 따라 필요 여부가 결정된다. 일반적으로 끄고 사용하자.
     *
     * @see Gradle 플러그인: https://plugins.gradle.org/plugin/org.gradle.toolchains.foojay-resolver-convention
     * @see Github: https://github.com/gradle/foojay-toolchains
     */
    id("org.gradle.toolchains.foojay-resolver-convention") apply false
}

include(":app:web-api:api")
include(":app:batch:example-batch")
include(":core:example-core")
include(":domain:example-domain")
include(":independent:example-independent")
include(":system:example-system")
