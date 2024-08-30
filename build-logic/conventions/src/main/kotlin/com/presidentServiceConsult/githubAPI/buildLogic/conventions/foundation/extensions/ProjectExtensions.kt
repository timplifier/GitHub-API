package com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions

import org.gradle.accessors.dm.LibrariesForAndroidProjectConfig
import org.gradle.accessors.dm.LibrariesForGradleProjectConfig
import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.provider.Provider
import org.gradle.plugin.use.PluginDependency
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

internal fun Project.alias(pluginDependency: Provider<PluginDependency>) {
    pluginManager.apply(pluginDependency.get().pluginId)
}

inline fun <reified T> Project.retrieveExtension(name: String): T {
    return extensions.getByName(name) as T
}

inline val Project.libs: LibrariesForLibs
    inline get() = retrieveExtension("libs")

inline val Project.gradleProjectConfig: LibrariesForGradleProjectConfig
    inline get() = retrieveExtension("gradleProjectConfig")
inline val Project.androidProjectConfig: LibrariesForAndroidProjectConfig
    inline get() = retrieveExtension("androidProjectConfig")

inline val Project.javaVersion
    get() = JavaVersion.values()[gradleProjectConfig.versions.kotlin.jvm.target.integer - 1]

inline val Project.jvmTarget
    get() = JvmTarget.values()
        .first { it.target.contains(gradleProjectConfig.versions.kotlin.jvm.target.get()) }