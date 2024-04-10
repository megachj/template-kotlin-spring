tasks.register("checkProjectDependenciesOnRules") {
    group = "sunset"
    description = "멀티 모듈 프로젝트에서 프로젝트간 의존성 규칙을 검사하는 태스크"

    doLast {
        println("[checkProjectDependenciesOnRules] 프로젝트 모듈들의 의존성 규칙 체크를 시작합니다.")

        subprojects
            .forEach { project ->
                println("sub project [${project.name}, path: ${project.path}]")
                val projectDependencies = fetchProjectDependencies(project)
                projectDependencies.forEach {
                    checkDependencyRules(project, it)
                }
                println()
            }

        println("[checkProjectDependenciesOnRules] 프로젝트 모듈들의 의존성 규칙 체크가 완료되었습니다.")
    }
}

fun fetchProjectDependencies(project: Project): List<ProjectDependency> {
    return project.configurations.flatMap { configuration ->
        configuration.dependencies.withType(ProjectDependency::class.java)
    }.distinct()
}

fun checkDependencyRules(project: Project, projectDependency: ProjectDependency) {
    val moduleToAbleDependencyMap = mapOf(
        "independent" to listOf<String>(),
        "core" to listOf(),
        "domain" to listOf("independent", "core", "domain"),
        "system" to listOf("independent", "core", "system"),
        "app" to listOf("independent", "core", "domain", "system")
    )

    println("checkProjectDependenciesOnRules 태스크: checkDependencyRules ${project.path} -> ${projectDependency.dependencyProject.path}")
    val moduleLayer = project.path.split(":")[1]
    val dependencyModuleLayer = projectDependency.dependencyProject.path.split(":")[1]
    if (!moduleToAbleDependencyMap[moduleLayer]?.contains(dependencyModuleLayer)!!) {
        throw GradleException("프로젝트간 의존성 규칙 위반입니다. `${project.path}` 가 `${projectDependency.dependencyProject.path}`를 의존하고 있습니다.")
    }
}
