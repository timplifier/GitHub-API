package com.presidentServiceConsult.githubAPI.domain.foundation

import kotlinx.serialization.Serializable

@Serializable
sealed class HttpError {

    @Serializable
    data object Timeout : HttpError()

    @Serializable
    class ApiInputs(
        val message: String,
        val status: String
    ) : HttpError()

    @Serializable
    class Api(val message: String) : HttpError()

    @Serializable
    class Unexpected(val message: String) : HttpError()
}