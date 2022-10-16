pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "NIAModularClone"
include(":app")
include(":feature:foryou")
include(":feature:author")
include(":feature:bookmarks")
include(":feature:interests")
include(":feature:topic")
