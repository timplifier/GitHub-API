enableFeaturePreview("STABLE_CONFIGURATION_CACHE")
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    versionCatalogs {

        create("pluginConfig") {
            from(files("gradle/plugin-config.versions.toml"))
        }

        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }

        create("gradleProjectConfig") {
            from(files("../gradle/gradle-project-config.versions.toml"))
        }
        create("androidProjectConfig") {
            from(files("../gradle/android-project-config.versions.toml"))
        }
    }
}

rootProject.name = "build-logic"
include(":conventions")