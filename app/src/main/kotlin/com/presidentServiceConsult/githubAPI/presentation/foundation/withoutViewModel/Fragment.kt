package com.presidentServiceConsult.githubAPI.presentation.foundation.withoutViewModel

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

abstract class Fragment<Binding : ViewBinding>(@LayoutRes layoutId: Int) : Fragment(layoutId) {
    protected abstract val binding: Binding

    final override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initialize()
        assembleViews()
        bindListeners()
        establishRequest()
        launchObservers()
    }

    protected open fun initialize() {}

    protected open fun assembleViews() {}

    protected open fun bindListeners() {}

    protected open fun establishRequest() {}

    protected open fun launchObservers() {}

    protected open fun View.navigateBack(
        navigate: NavController.() -> Unit = {
            hideKeyboard()
            findNavController().navigateUp()
        }
    ) {
        setOnClickListener {
            navigate(findNavController())
        }
    }

    protected fun View.navigate(
        navigate: NavController.() -> Unit
    ) {
        setOnClickListener {
            hideKeyboard()
            navigate(findNavController())
        }
    }

    protected fun <ViewHolder : RecyclerView.ViewHolder> RecyclerView.constructRecyclerView(
        adapter: RecyclerView.Adapter<ViewHolder>,
        construct: RecyclerView.(adapter: RecyclerView.Adapter<ViewHolder>) -> Unit = {}
    ) {
        this.adapter = adapter
        construct(this, adapter)
    }

    protected fun <T> Flow<T>.spectateStateFlow(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        actionWhenCollected: suspend (T) -> Unit
    ) {
        launchReactiveCoroutine(lifecycleState) {
            collectLatest {
                actionWhenCollected(it)
            }
        }
    }

    protected fun launchReactiveCoroutine(
        lifecycleState: Lifecycle.State = Lifecycle.State.STARTED,
        gather: suspend (coroutineScope: CoroutineScope) -> Unit,
    ): Job {
        return viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(lifecycleState) {
                gather(this)
            }
        }
    }

    protected fun <T> createAsyncForList(
        list: List<T>,
        actionWithAsync: suspend (element: T) -> Unit,
        actionWhenEverythingIsDone: (data: Any) -> Unit
    ) {
        launchReactiveCoroutine { coroutineScope ->
            list.map { element ->
                coroutineScope.async(Dispatchers.IO) {
                    actionWithAsync(element)
                }
            }.awaitAll().also {
                actionWhenEverythingIsDone(it)
            }
        }
    }

    protected fun hideKeyboard() {
        val inputMethodManager =
            context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    protected fun overrideOnBackPressed(onBackPressed: OnBackPressedCallback.() -> Unit) {
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            onBackPressed()
        }
    }

    protected fun <T : Any> setBackStackData(key: String, data: T?, doBack: Boolean) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, data)
        if (doBack) {
            findNavController().navigateUp()
        }
    }

    protected suspend fun <T : Any> getBackStackData(
        key: String,
        initialValue: T,
        result: (T) -> (Unit)
    ) {
        findNavController().currentBackStackEntry?.savedStateHandle?.getStateFlow(key, initialValue)
            ?.collectLatest {
                result(it)
            }
    }
}