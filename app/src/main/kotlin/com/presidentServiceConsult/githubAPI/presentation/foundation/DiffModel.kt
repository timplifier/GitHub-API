package com.presidentServiceConsult.githubAPI.presentation.foundation

interface DiffModel<T> {
    val id: T?
    override fun equals(other: Any?): Boolean
}