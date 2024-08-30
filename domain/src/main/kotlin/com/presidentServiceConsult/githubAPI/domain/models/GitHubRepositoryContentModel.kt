package com.presidentServiceConsult.githubAPI.domain.models

data class GitHubRepositoryContentModel(
    val type: String,
    val size: Int,
    val name: String,
    val path: String,
    val sha: String,
    val url: String,
    val gitUrl: String,
    val htmlUrl: String,
    val downloadUrl: String?,
    val links: GitHubRepositoryContentLinksModel
)