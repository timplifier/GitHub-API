package com.presidentServiceConsult.githubAPI.data.foundation

import kotlinx.collections.immutable.ImmutableList
import kotlinx.serialization.Serializable

@Serializable
data class PagingResponse<T>(
    val totalCount: Int,
    val incompleteResults: Boolean,
    @Serializable(with = ImmutableListSerializer::class)
    val items: ImmutableList<T>,
    val next: Int? = null,
    val prev: Int? = null
)