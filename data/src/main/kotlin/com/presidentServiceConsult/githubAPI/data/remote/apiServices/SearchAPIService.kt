package com.presidentServiceConsult.githubAPI.data.remote.apiServices

import com.presidentServiceConsult.githubAPI.data.foundation.PagingResponse
import com.presidentServiceConsult.githubAPI.data.remote.dtos.GitHubRepositoryDTO
import com.presidentServiceConsult.githubAPI.data.remote.dtos.GitHubUserDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchAPIService {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") query: String,
    ): Response<PagingResponse<GitHubUserDTO>>

    @GET("search/repositories")
    suspend fun searchRepositories(
        @Query("q") query: String,
    ): Response<PagingResponse<GitHubRepositoryDTO>>
}