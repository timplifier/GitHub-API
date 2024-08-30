import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.agp.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.parcelize)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {

    val buildConfig = androidProjectConfig.versions.build
    val sdkConfig = androidProjectConfig.versions.sdk
    namespace = buildConfig.namespace.get()
    compileSdk = sdkConfig.compile.get().toInt()

    defaultConfig {
        applicationId = buildConfig.namespace.get()
        minSdk = sdkConfig.min.get().toInt()
        targetSdk = sdkConfig.target.get().toInt()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
            isShrinkResources = false
        }
    }

    compileOptions {
        sourceCompatibility =
            JavaVersion.values()[gradleProjectConfig.versions.kotlin.jvm.target.get()
                .toInt() - 1]
        targetCompatibility =
            JavaVersion.values()[gradleProjectConfig.versions.kotlin.jvm.target.get()
                .toInt() - 1]
    }

    kotlinOptions {
        jvmTarget = JvmTarget.values()
            .first { it.target.contains(gradleProjectConfig.versions.kotlin.jvm.target.get()) }.target
    }

    buildFeatures.viewBinding = true
}

dependencies {
    implementation(projects.data)
    implementation(projects.domain)
    implementation(libs.bundles.androidx.ui)
    implementation(libs.bundles.kotlinx.android)
    implementation(libs.bundles.koin.android)
}