package com.presidentServiceConsult.githubAPI.presentation.models

import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryContentLinksModel

data class GitHubRepositoryContentLinksUI(
    val self: String,
    val git: String,
    val html: String
)

inline val GitHubRepositoryContentLinksModel.ui
    get() = GitHubRepositoryContentLinksUI(self, git, html)