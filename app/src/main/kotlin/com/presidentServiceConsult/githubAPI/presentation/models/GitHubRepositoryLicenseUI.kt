package com.presidentServiceConsult.githubAPI.presentation.models

import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryLicenseModel

data class GitHubRepositoryLicenseUI(
    val key: String,
    val name: String,
    val url: String,
    val spdxId: String,
    val nodeId: String,
    val htmlUrl: String
)

inline val GitHubRepositoryLicenseModel.ui
    get() = GitHubRepositoryLicenseUI(
        key,
        name,
        url,
        spdxId,
        nodeId,
        htmlUrl
    )