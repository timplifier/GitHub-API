package com.presidentServiceConsult.githubAPI.presentation.models

import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryContentModel
import com.presidentServiceConsult.githubAPI.presentation.foundation.DiffModel
import kotlin.random.Random

data class GitHubRepositoryContentUI(
    override val id: Int = Random.nextInt(),
    val type: String,
    val size: Int,
    val name: String,
    val path: String,
    val sha: String,
    val url: String,
    val gitUrl: String,
    val htmlUrl: String,
    val downloadUrl: String?,
    val links: GitHubRepositoryContentLinksUI
) : DiffModel<Int>

inline val GitHubRepositoryContentModel.ui
    get() = GitHubRepositoryContentUI(
        Random.nextInt(),
        type,
        size,
        name,
        path,
        sha,
        url,
        gitUrl,
        htmlUrl,
        downloadUrl,
        links.ui
    )