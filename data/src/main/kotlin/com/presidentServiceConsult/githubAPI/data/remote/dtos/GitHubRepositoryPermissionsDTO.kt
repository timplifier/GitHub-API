package com.presidentServiceConsult.githubAPI.data.remote.dtos

import com.presidentServiceConsult.githubAPI.data.foundation.DTOMapper
import com.presidentServiceConsult.githubAPI.domain.models.GitHubRepositoryPermissionsModel
import kotlinx.serialization.Serializable

@Serializable
data class GitHubRepositoryPermissionsDTO(
    val admin: Boolean,
    val maintain: Boolean,
    val push: Boolean,
    val triage: Boolean,
    val pull: Boolean
) : DTOMapper<GitHubRepositoryPermissionsModel> {
    override fun toDomain(): GitHubRepositoryPermissionsModel {
        return GitHubRepositoryPermissionsModel(
            admin,
            maintain,
            push,
            triage,
            pull
        )
    }
}