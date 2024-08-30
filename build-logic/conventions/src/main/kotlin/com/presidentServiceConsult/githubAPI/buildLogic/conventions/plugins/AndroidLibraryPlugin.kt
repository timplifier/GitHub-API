package com.presidentServiceConsult.githubAPI.buildLogic.conventions.plugins

import com.android.build.api.dsl.LibraryExtension
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.alias
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.androidProjectConfig
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.implementation
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.integer
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.javaVersion
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.jvmTarget
import com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

internal class AndroidLibraryPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            alias(libs.plugins.agp.library)
            alias(libs.plugins.kotlin.android)
            alias(libs.plugins.kotlin.serialization)

            extensions.configure<LibraryExtension> {

                namespace =
                    "${androidProjectConfig.versions.build.namespace.get()}.${
                        project.name
                    }"

                compileSdk = androidProjectConfig.versions.sdk.compile.integer

                defaultConfig {
                    minSdk = androidProjectConfig.versions.sdk.min.integer

                    consumerProguardFiles("consumer-rules.pro")
                }

                compileOptions {
                    sourceCompatibility = javaVersion
                    targetCompatibility = javaVersion
                }
            }

            configure<JavaPluginExtension> {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }

            extensions.configure<KotlinAndroidProjectExtension> {
                compilerOptions {
                    this.jvmTarget.set(this@with.jvmTarget)
                }
            }

            dependencies {
                implementation(libs.koin.core)
                implementation(libs.bundles.kotlinx.core)
            }
        }
    }
}