package com.presidentServiceConsult.githubAPI.presentation.foundation

import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.presidentServiceConsult.githubAPI.domain.foundation.HttpError
import com.presidentServiceConsult.githubAPI.presentation.foundation.withoutViewModel.Fragment
import kotlinx.coroutines.flow.Flow

abstract class Fragment<Binding : ViewBinding, ViewModel : androidx.lifecycle.ViewModel>(
    @LayoutRes layoutId: Int
) :
    Fragment<Binding>(layoutId) {

    protected abstract val viewModel: ViewModel

    protected fun <T> Flow<UIState<T>>.spectateUIStateReactively(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        onStateCollected: (suspend (state: UIState<T>) -> Unit)? = null,
        success: (suspend (data: T) -> Unit)? = null,
        loading: (suspend (data: UIState.Loading<T>) -> Unit)? = null,
        error: (suspend (error: String) -> Unit)? = null,
        idle: (suspend (idle: UIState.Idle<T>) -> Unit)? = null,
        onStateSpectated: (suspend () -> Unit)? = null
    ) {

        spectateStateFlow(lifecycleState) { uiState ->
            onStateCollected?.invoke(uiState)
            when (uiState) {
                is UIState.Error -> error?.invoke(uiState.error.unwrapError())
                is UIState.Idle -> idle?.invoke(uiState)
                is UIState.Loading -> loading?.invoke(uiState)
                is UIState.Success -> success?.invoke(uiState.data)
            }
            onStateSpectated?.invoke()
        }
    }

    private fun HttpError.unwrapError(): String {
        return when (this) {
            is HttpError.Timeout ->
                "Timeout"

            is HttpError.Unexpected ->
                message

            is HttpError.Api ->
                message

            is HttpError.ApiInputs ->
                "$message - $status"
        }
    }
}