package com.presidentServiceConsult.githubAPI.presentation.ui.fragments.main.repositoryDetails

import com.presidentServiceConsult.githubAPI.domain.useCases.GetRepositoryContentsUseCase
import com.presidentServiceConsult.githubAPI.presentation.foundation.UIState
import com.presidentServiceConsult.githubAPI.presentation.foundation.ViewModel
import com.presidentServiceConsult.githubAPI.presentation.models.GitHubRepositoryContentUI
import com.presidentServiceConsult.githubAPI.presentation.models.ui
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.asStateFlow

class RepositoryDetailsViewModel(private val getRepositoryContentsUseCase: GetRepositoryContentsUseCase) :
    ViewModel() {

    private val _repositoryContents = mutableUIStateFlow<ImmutableList<GitHubRepositoryContentUI>>()
    val repositoryContents =
        _repositoryContents.asStateFlow()

    fun getRepositoryContents(owner: String, repository: String, path: String) {
        if (_repositoryContents.value !is UIState.Success)
            getRepositoryContentsUseCase(owner, repository, path).gatherNetworkRequest(
                _repositoryContents
            ) { repositoryContents ->
                repositoryContents.map { repositoryContent -> repositoryContent.ui }
                    .toPersistentList()
            }
    }
}