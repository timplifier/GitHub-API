package com.presidentServiceConsult.githubAPI.data.remote.dtos


import com.presidentServiceConsult.githubAPI.data.foundation.DTOMapper
import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryLicenseModel
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoryLicenseDTO(
    @SerialName("key")
    val key: String,
    @SerialName("name")
    val name: String,
    @SerialName("url")
    val url: String? = null,
    val spdxId: String,
    val nodeId: String,
    val htmlUrl: String? = null
) : DTOMapper<GitHubRepositoryLicenseModel> {
    override fun toDomain(): GitHubRepositoryLicenseModel {
        return GitHubRepositoryLicenseModel(
            key,
            name,
            url ?: "",
            spdxId,
            nodeId,
            htmlUrl ?: ""
        )
    }
}