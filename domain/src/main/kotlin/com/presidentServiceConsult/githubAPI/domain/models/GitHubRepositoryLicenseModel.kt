package com.presidentServiceConsult.githubAPI.domain.models

data class GitHubRepositoryLicenseModel(
    val key: String,
    val name: String,
    val url: String,
    val spdxId: String,
    val nodeId: String,
    val htmlUrl: String
)