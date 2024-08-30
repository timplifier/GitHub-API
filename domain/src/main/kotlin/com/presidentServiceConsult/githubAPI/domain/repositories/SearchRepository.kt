package com.presidentServiceConsult.githubAPI.domain.repositories

import com.presidentServiceConsult.githubAPI.domain.foundation.Either
import com.presidentServiceConsult.githubAPI.domain.foundation.HttpError
import com.presidentServiceConsult.githubAPI.domain.models.GitHubData
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun searchUsersAndRepositories(query: String): Flow<Either<HttpError, ImmutableList<GitHubData>>>
}