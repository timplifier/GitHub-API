package com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions

import org.gradle.kotlin.dsl.DependencyHandlerScope

internal fun DependencyHandlerScope.implementation(dependencyNotation: Any) {
    "implementation"(dependencyNotation)
}

internal fun DependencyHandlerScope.api(dependencyNotation: Any) {
    "api"(dependencyNotation)
}