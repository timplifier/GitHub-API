package com.presidentServiceConsult.githubAPI.domain.useCases

import com.presidentServiceConsult.githubAPI.domain.repositories.SearchRepository

class SearchUsersAndRepositoriesUseCase(private val searchRepository: SearchRepository) {
    suspend operator fun invoke(query: String) = searchRepository.searchUsersAndRepositories(query)
}