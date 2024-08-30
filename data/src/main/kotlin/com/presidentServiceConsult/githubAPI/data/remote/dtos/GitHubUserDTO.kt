package com.presidentServiceConsult.githubAPI.data.remote.dtos


import com.presidentServiceConsult.githubAPI.data.foundation.DTOMapper
import com.presidentServiceConsult.githubAPI.domain.models.GitHubData
import kotlinx.serialization.Serializable

@Serializable
data class GitHubUserDTO(
    val login: String,
    val id: Int,
    val nodeId: String,
    val avatarUrl: String,
    val gravatarId: String,
    val url: String,
    val htmlUrl: String? = null,
    val followersUrl: String,
    val followingUrl: String,
    val gistsUrl: String,
    val starredUrl: String,
    val subscriptionsUrl: String,
    val organizationsUrl: String,
    val reposUrl: String,
    val eventsUrl: String,
    val receivedEventsUrl: String,
    val type: String,
    val siteAdmin: Boolean,
    val score: Double? = null,
) : DTOMapper<GitHubData.GitHubUserModel> {
    override fun toDomain(): GitHubData.GitHubUserModel {
        return GitHubData.GitHubUserModel(
            login,
            id,
            nodeId,
            avatarUrl,
            gravatarId,
            url,
            htmlUrl ?: "",
            followersUrl,
            followingUrl,
            gistsUrl,
            starredUrl,
            subscriptionsUrl,
            organizationsUrl,
            reposUrl,
            eventsUrl,
            receivedEventsUrl,
            type,
            siteAdmin,
            score ?: 0.0,
        )
    }
}