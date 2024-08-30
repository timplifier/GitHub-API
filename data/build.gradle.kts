plugins {
    alias(libs.plugins.githubapi.library.android)
}

android {
    buildFeatures.buildConfig = true
    buildTypes {
        release {
            isMinifyEnabled = true
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }
        debug {
            isMinifyEnabled = false
            buildConfigField("String", "BASE_URL", "\"https://api.github.com/\"")
        }
    }
}

dependencies {
    implementation(projects.domain)
    implementation(libs.bundles.squareup.retrofit)
}