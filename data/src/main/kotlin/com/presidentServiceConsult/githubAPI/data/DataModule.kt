package com.presidentServiceConsult.githubAPI.data

import android.content.Context
import com.presidentServiceConsult.githubAPI.data.local.SharedPreferencesGenerator
import com.presidentServiceConsult.githubAPI.data.local.UserPreferences
import com.presidentServiceConsult.githubAPI.data.remote.apiServices.RepositoryAPIService
import com.presidentServiceConsult.githubAPI.data.remote.apiServices.SearchAPIService
import com.presidentServiceConsult.githubAPI.data.remote.interceptors.ApplicationVndGithubJsonInterceptor
import com.presidentServiceConsult.githubAPI.data.repositories.GitHubRepositoryRepositoryImplementation
import com.presidentServiceConsult.githubAPI.data.repositories.SearchRepositoryImplementation
import com.presidentServiceConsult.githubAPI.domain.repositories.GitHubRepositoryRepository
import com.presidentServiceConsult.githubAPI.domain.repositories.SearchRepository
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonNamingStrategy
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    singleOf(::generateJson)
    singleOf(::generateSharedPreferences)
    singleOf(::UserPreferences)
    singleOf(::generateRetrofit)
    singleOf(::generateSearchAPIService)
    singleOf(::generateRepositoryAPIService)
    singleOf(::ApplicationVndGithubJsonInterceptor)
    singleOf(::SearchRepositoryImplementation) bind SearchRepository::class
    singleOf(::GitHubRepositoryRepositoryImplementation) bind GitHubRepositoryRepository::class
}

@OptIn(ExperimentalSerializationApi::class)
private fun generateJson() = Json {
    encodeDefaults = true
    isLenient = true
    ignoreUnknownKeys = true
    explicitNulls = true
    prettyPrint = true
    allowComments = true
    namingStrategy = JsonNamingStrategy.SnakeCase
}

private fun generateSharedPreferences(context: Context) =
    SharedPreferencesGenerator(context).generateSharedPreferences()

private fun generateRetrofit(
    applicationVndGithubJsonInterceptor: ApplicationVndGithubJsonInterceptor,
    json: Json
) =
    Retrofit.Builder()
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().setLevel(
                        when {
                            BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                            else -> HttpLoggingInterceptor.Level.NONE
                        }
                    )
                )
                .addInterceptor(applicationVndGithubJsonInterceptor)
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        )
        .baseUrl(BuildConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
        .build()

private fun generateSearchAPIService(retrofit: Retrofit): SearchAPIService =
    retrofit.create(SearchAPIService::class.java)

private fun generateRepositoryAPIService(retrofit: Retrofit): RepositoryAPIService =
    retrofit.create(RepositoryAPIService::class.java)
