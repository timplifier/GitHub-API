package com.presidentServiceConsult.githubAPI.data.remote.apiServices

import com.presidentServiceConsult.githubAPI.data.remote.dtos.GitHubRepositoryContentDTO
import kotlinx.collections.immutable.ImmutableList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RepositoryAPIService {

    @GET("repos/{owner}/{repo}/contents/{path}")
    suspend fun getRepositoryContents(
        @Path("owner") owner: String,
        @Path("repo") repo: String,
        @Path("path") path: String
    ): Response<ArrayList<GitHubRepositoryContentDTO>>
}