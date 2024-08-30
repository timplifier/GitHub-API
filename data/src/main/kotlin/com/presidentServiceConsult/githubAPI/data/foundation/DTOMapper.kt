package com.presidentServiceConsult.githubAPI.data.foundation

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

interface DTOMapper<T> {
    fun toDomain(): T
}

fun <T> List<DTOMapper<T>>.toDomain(): ImmutableList<T> = map { it.toDomain() }.toImmutableList()