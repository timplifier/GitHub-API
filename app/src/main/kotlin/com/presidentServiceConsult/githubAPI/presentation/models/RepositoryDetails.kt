package com.presidentServiceConsult.githubAPI.presentation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class RepositoryDetails(
    val repositoryOwner: String,
    val repositoryName: String,
    val repositoryPath: String
) : Parcelable