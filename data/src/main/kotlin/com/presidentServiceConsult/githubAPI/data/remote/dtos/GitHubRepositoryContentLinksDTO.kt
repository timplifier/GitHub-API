package com.presidentServiceConsult.githubAPI.data.remote.dtos


import com.presidentServiceConsult.githubAPI.data.foundation.DTOMapper
import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryContentLinksModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoryContentLinksDTO(
    @SerialName("self")
    val self: String,
    @SerialName("git")
    val git: String,
    @SerialName("html")
    val html: String
) : DTOMapper<GitHubRepositoryContentLinksModel> {
    override fun toDomain(): GitHubRepositoryContentLinksModel {
        return GitHubRepositoryContentLinksModel(
            self,
            git,
            html
        )
    }
}