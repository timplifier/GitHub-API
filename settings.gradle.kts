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
    }
}
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
    versionCatalogs {
        create("gradleProjectConfig") {
            from(files("gradle/gradle-project-config.versions.toml"))
        }

        create("androidProjectConfig") {
            from(files("gradle/android-project-config.versions.toml"))
        }
    }
}

rootProject.name = "github-api"
includeBuild("build-logic")
include(
    ":app",
    ":data",
    ":domain"
)
