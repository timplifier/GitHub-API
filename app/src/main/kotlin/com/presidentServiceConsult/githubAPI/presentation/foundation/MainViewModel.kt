package com.presidentServiceConsult.githubAPI.presentation.foundation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.presidentServiceConsult.githubAPI.domain.foundation.Either
import com.presidentServiceConsult.githubAPI.domain.foundation.HttpError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

abstract class ViewModel : ViewModel() {

    protected fun <T> mutableUIStateFlow() = MutableStateFlow<UIState<T>>(UIState.Idle())

    protected fun <T> Flow<Either<HttpError, T>>.gatherNetworkRequest(
        state: MutableStateFlow<UIState<T>> = mutableUIStateFlow(),
    ) = gatherRequest(state) {
        it
    }

    protected fun <T, S> Flow<Either<HttpError, T>>.gatherNetworkRequest(
        state: MutableStateFlow<UIState<S>>,
        mapToUI: (T) -> S,
    ) = gatherRequest(state) {
        mapToUI(it)
    }

    private fun <T, S> Flow<Either<HttpError, T>>.gatherRequest(
        state: MutableStateFlow<UIState<S>>,
        mapToUI: (data: T) -> S,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            state.value = UIState.Loading()
            this@gatherRequest.collect {
                when (it) {
                    is Either.Left -> {
                        state.value = UIState.Error(it.value)
                    }

                    is Either.Right -> {
                        state.value = UIState.Success(mapToUI(it.value))
                    }
                }
            }
        }
    }
}