package com.presidentServiceConsult.githubAPI.buildLogic.conventions.foundation.extensions

import org.gradle.api.provider.Provider

inline val Provider<String>.integer
    get() = get().toInt()