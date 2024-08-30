package com.presidentServiceConsult.githubAPI.data.repositories

import com.presidentServiceConsult.githubAPI.data.foundation.RepositoryImplementation
import com.presidentServiceConsult.githubAPI.data.remote.apiServices.SearchAPIService
import com.presidentServiceConsult.githubAPI.domain.foundation.Either
import com.presidentServiceConsult.githubAPI.domain.foundation.HttpError
import com.presidentServiceConsult.githubAPI.domain.foundation.isErrorPresent
import com.presidentServiceConsult.githubAPI.domain.models.GitHubData
import com.presidentServiceConsult.githubAPI.domain.repositories.SearchRepository
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.withContext

class SearchRepositoryImplementation(private val searchAPIService: SearchAPIService) :
    RepositoryImplementation(), SearchRepository {

    private fun searchUsers(query: String): Flow<Either<HttpError, ImmutableList<GitHubData.GitHubUserModel>>> {
        return makeNetworkRequest { searchAPIService.searchUsers(query) }
    }

    private fun searchRepositories(query: String): Flow<Either<HttpError, ImmutableList<GitHubData.GitHubRepositoryModel>>> {
        return makeNetworkRequest { searchAPIService.searchRepositories(query) }
    }

    override suspend fun searchUsersAndRepositories(query: String): Flow<Either<HttpError, ImmutableList<GitHubData>>> {
        return withContext(Dispatchers.IO) {

            val deferredUsers = async {
                searchUsers(query)
            }

            val deferredRepositories = async {
                searchRepositories(query)
            }

            deferredUsers.await().combine(deferredRepositories.await()) { users, repositories ->
                val gitHubData = mutableListOf<GitHubData>()

                users.unwrap {
                    gitHubData.addAll(value.sortedBy { it.login })
                }

                repositories.unwrap {
                    gitHubData.addAll(value.sortedBy { it.name })
                }

                when (gitHubData.isNotEmpty()) {
                    true -> Either.Right(gitHubData.toImmutableList())
                    false -> when {
                        users.isErrorPresent() -> users
                        repositories.isErrorPresent() -> repositories
                        else -> Either.Left(HttpError.Unexpected("Unexpected"))
                    }
                }
            }
        }
    }
}