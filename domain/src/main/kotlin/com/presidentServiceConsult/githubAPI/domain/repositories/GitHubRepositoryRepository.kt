package com.presidentServiceConsult.githubAPI.domain.repositories

import com.presidentServiceConsult.githubAPI.domain.foundation.Either
import com.presidentServiceConsult.githubAPI.domain.foundation.HttpError
import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryContentModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.flow.Flow

interface GitHubRepositoryRepository {
    fun getRepositoryContents(
        owner: String,
        repositoryName: String,
        path: String
    ): Flow<Either<HttpError, ImmutableList<GitHubRepositoryContentModel>>>
}