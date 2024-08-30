package com.presidentServiceConsult.githubAPI.presentation.ui.fragments.main

import androidx.lifecycle.viewModelScope
import com.presidentServiceConsult.githubAPI.domain.models.GitHubData
import com.presidentServiceConsult.githubAPI.domain.useCases.SearchUsersAndRepositoriesUseCase
import com.presidentServiceConsult.githubAPI.presentation.foundation.ViewModel
import com.presidentServiceConsult.githubAPI.presentation.models.GitHubDataUI
import com.presidentServiceConsult.githubAPI.presentation.models.ui
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(
    private val searchUsersAndRepositoriesUseCase: SearchUsersAndRepositoriesUseCase
) : ViewModel() {

    private val _usersAndRepositories = mutableUIStateFlow<ImmutableList<GitHubDataUI>>()
    val userAndRepositories = _usersAndRepositories.asStateFlow()

    fun searchUsersAndRepositories(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            searchUsersAndRepositoriesUseCase(query).gatherNetworkRequest(_usersAndRepositories) { gitHubData ->
                gitHubData.map { gitHubDataElement ->
                    when (gitHubDataElement) {
                        is GitHubData.GitHubRepositoryModel -> gitHubDataElement.ui
                        is GitHubData.GitHubUserModel -> gitHubDataElement.ui
                    }
                }.toImmutableList()
            }
        }
    }
}