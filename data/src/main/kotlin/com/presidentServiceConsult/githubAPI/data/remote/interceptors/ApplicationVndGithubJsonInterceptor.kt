package com.presidentServiceConsult.githubAPI.data.remote.interceptors

import com.presidentServiceConsult.githubAPI.data.local.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

class ApplicationVndGithubJsonInterceptor(private val userPreferences: UserPreferences) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(
            chain.request().newBuilder()
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("X-GitHub-Api-Version", "2022-11-28")
                .addHeader("Authorization", "Bearer ${userPreferences.githubAPIToken}").build()
        )
    }
}