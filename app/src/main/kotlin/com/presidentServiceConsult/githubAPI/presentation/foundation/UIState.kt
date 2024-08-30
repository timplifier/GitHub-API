package com.presidentServiceConsult.githubAPI.presentation.foundation

import com.presidentServiceConsult.githubAPI.domain.foundation.HttpError

sealed class UIState<out T> {

    class Idle<T> : UIState<T>()

    class Loading<T> : UIState<T>()

    class Error<T>(val error: HttpError) : UIState<T>()

    class Success<T>(val data: T) : UIState<T>()
}