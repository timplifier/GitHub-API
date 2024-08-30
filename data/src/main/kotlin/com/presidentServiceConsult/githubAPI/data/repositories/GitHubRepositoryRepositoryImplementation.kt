package com.presidentServiceConsult.githubAPI.data.repositories

import com.presidentServiceConsult.githubAPI.data.foundation.RepositoryImplementation
import com.presidentServiceConsult.githubAPI.data.remote.apiServices.RepositoryAPIService
import com.presidentServiceConsult.githubAPI.domain.foundation.Either
import com.presidentServiceConsult.githubAPI.domain.foundation.HttpError
import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryContentModel
import com.presidentServiceConsult.githubAPI.domain.repositories.GitHubRepositoryRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.Flow

class GitHubRepositoryRepositoryImplementation(private val repositoryAPIService: RepositoryAPIService) :
    RepositoryImplementation(), GitHubRepositoryRepository {

    override fun getRepositoryContents(
        owner: String,
        repositoryName: String,
        path: String
    ): Flow<Either<HttpError, ImmutableList<GitHubRepositoryContentModel>>> {
        return makeNetworkRequestPlainList({
            repositoryAPIService.getRepositoryContents(
                owner,
                repositoryName,
                path
            )
        }) {
            it.sortedByDescending { it.type == "dir" }
        }
    }
}