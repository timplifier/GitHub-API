package com.presidentServiceConsult.githubAPI.domain.models

data class GitHubRepositoryPermissionsModel(
    val admin: Boolean,
    val maintain: Boolean,
    val push: Boolean,
    val triage: Boolean,
    val pull: Boolean
)