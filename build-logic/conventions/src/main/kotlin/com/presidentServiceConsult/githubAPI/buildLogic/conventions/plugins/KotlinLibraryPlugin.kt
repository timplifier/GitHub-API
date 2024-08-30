package com.presidentServiceConsult.githubAPI.buildLogic.conventions.plugins

import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.alias
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.implementation
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.javaVersion
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

internal class KotlinLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            alias(libs.plugins.java.library)
            alias(libs.plugins.kotlin.jvm)
            alias(libs.plugins.kotlin.serialization)

            dependencies {
                implementation(libs.bundles.kotlinx.core)
                implementation(libs.koin.core)
            }

            configure<JavaPluginExtension> {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }
        }
    }
}