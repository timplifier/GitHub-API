package com.presidentServiceConsult.githubAPI.data.remote.dtos


import com.presidentServiceConsult.githubAPI.data.foundation.DTOMapper
import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryContentModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoryContentDTO(
    @SerialName("type")
    val type: String,
    @SerialName("size")
    val size: Int,
    @SerialName("name")
    val name: String,
    @SerialName("path")
    val path: String,
    @SerialName("sha")
    val sha: String,
    @SerialName("url")
    val url: String,
//    @SerialName("git_url")
    val gitUrl: String,
//    @SerialName("html_url")
    val htmlUrl: String,
//    @SerialName("download_url")
    val downloadUrl: String?,
    @SerialName("_links")
    val links: GitHubRepositoryContentLinksDTO
) : DTOMapper<GitHubRepositoryContentModel> {
    override fun toDomain(): GitHubRepositoryContentModel {
        return GitHubRepositoryContentModel(
            type,
            size,
            name,
            path,
            sha,
            url,
            gitUrl,
            htmlUrl,
            downloadUrl,
            links.toDomain()
        )
    }
}