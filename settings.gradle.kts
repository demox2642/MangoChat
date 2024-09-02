pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "MangoChat"
include(":app")
include(":base:presentation")
include(":auth:presentation")
include(":chat:presentation")
include(":profile:presentation")
include(":auth:data")
include(":auth:domain")
include(":chat:domain")
include(":profile:domain")
include(":base:data")
include(":chat:data")
include(":profile:data")
include(":base:domain")
