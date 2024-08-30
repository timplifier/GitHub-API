package com.presidentServiceConsult.githubAPI.presentation.models

import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryPermissionsModel

data class GitHubRepositoryPermissionsUI(
    val admin: Boolean,
    val maintain: Boolean,
    val push: Boolean,
    val triage: Boolean,
    val pull: Boolean
)

inline val GitHubRepositoryPermissionsModel.ui
    get() = GitHubRepositoryPermissionsUI(
        admin,
        maintain,
        push,
        triage,
        pull
    )