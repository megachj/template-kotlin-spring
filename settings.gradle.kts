rootProject.name = "template-kotlin-spring"

pluginManagement {
    repositories {
        gradlePluginPortal()
    }

    val pluginNamespaces = mapOf(
        "org.jetbrains.kotlin" to "1.9.23",
        "org.jetbrains.kotlin.plugin" to "1.9.23",
    )

    val pluginIds = mapOf(
        "org.springframework.boot" to "3.2.4",
        "io.spring.dependency-management" to "1.1.4",
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

//plugins {
//    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
//}


