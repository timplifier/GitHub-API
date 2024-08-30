package com.presidentServiceConsult.githubAPI.data.foundation

import com.presidentServiceConsult.githubAPI.domain.foundation.Either
import com.presidentServiceConsult.githubAPI.domain.foundation.HttpError
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.serialization.json.Json
import okhttp3.ResponseBody
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.Response
import java.io.InterruptedIOException

abstract class RepositoryImplementation : KoinComponent {
    private val json by inject<Json>()

    private fun <T, S> doNetworkRequest(
        request: suspend () -> Response<T>,
        successful: (T) -> Either.Right<S>
    ) = flow {
        request().let {
            when {
                it.isSuccessful && it.body() != null -> {
                    emit(successful.invoke(it.body()!!))
                }

                !it.isSuccessful && it.code() >= 400 -> {
                    emit(Either.Left(it.errorBody().toApiError<HttpError.ApiInputs>()))
                }

                else -> {
                    emit(Either.Left(HttpError.Api(it.errorBody().toApiError())))
                }
            }
        }
    }.flowOn(Dispatchers.IO).catch { exception ->
        when (exception) {
            is InterruptedIOException -> {
                emit(Either.Left(HttpError.Timeout))
            }

            else -> {
                val message = exception.localizedMessage ?: "Error Occurred!"
                emit(Either.Left(HttpError.Unexpected(message)))
            }
        }
    }


    internal fun <DTO : DTOMapper<Domain>, Domain : Any> makeNetworkRequest(request: suspend () -> Response<PagingResponse<DTO>>): Flow<Either<HttpError, ImmutableList<Domain>>> {
        return doNetworkRequest(request) { body ->
            Either.Right(body.items.toDomain())
        }
    }

    internal fun <DTO : DTOMapper<Domain>, Domain : Any> makeNetworkRequestPlainList(
        request: suspend () -> Response<ArrayList<DTO>>,
        transform: (ArrayList<DTO>) -> List<DTO>
    ): Flow<Either<HttpError, ImmutableList<Domain>>> {
        return doNetworkRequest(request) { body ->
            Either.Right(transform(body).toDomain())
        }
    }

    private inline fun <reified T> ResponseBody?.toApiError(): T {
        return this?.let { json.decodeFromString<T>(it.string()) } ?: throw NullPointerException(
            "JsonUtil cannot convert fromJson: ${T::class.java.simpleName}"
        )
    }
}