package com.presidentServiceConsult.githubAPI.domain.useCases

import com.presidentServiceConsult.githubAPI.domain.repositories.GitHubRepositoryRepository

class GetRepositoryContentsUseCase(private val gitHubRepositoryRepository: GitHubRepositoryRepository) {
    operator fun invoke(owner: String, repositoryName: String, path: String) =
        gitHubRepositoryRepository.getRepositoryContents(owner, repositoryName, path)
}