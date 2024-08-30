package com.presidentServiceConsult.githubAPI.presentation.ui.fragments.main

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.presidentServiceConsult.githubAPI.R
import com.presidentServiceConsult.githubAPI.databinding.FragmentMainBinding
import com.presidentServiceConsult.githubAPI.presentation.foundation.Fragment
import com.presidentServiceConsult.githubAPI.presentation.foundation.UIState
import com.presidentServiceConsult.githubAPI.presentation.models.GitHubDataUI
import com.presidentServiceConsult.githubAPI.presentation.models.RepositoryDetails
import com.presidentServiceConsult.githubAPI.presentation.ui.adapters.UserAndRepositoriesAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment<FragmentMainBinding, MainViewModel>(R.layout.fragment_main) {
    override val binding by viewBinding(FragmentMainBinding::bind)
    override val viewModel by viewModel<MainViewModel>()
    private val userAndRepositoriesAdapter by lazy {
        UserAndRepositoriesAdapter(::onUserClick, ::onRepositoryClick)
    }

    override fun assembleViews() {
        binding.rvUsersAndRepositories.constructRecyclerView(userAndRepositoriesAdapter) {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun bindListeners() {
        bindSearch()
    }

    private fun bindSearch() = with(binding) {
        binding.etSearch.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH && etSearch.text.toString().length >= 3) {
                fetchUsersAndRepositories()
            }
            false
        }
        etSearch.addTextChangedListener {
            sivSearch.isEnabled = it.toString().length >= 3
        }

        listOf(sivSearch, iErrorTryAgain.btnTryAgain).forEach {
            it.setOnClickListener {
                fetchUsersAndRepositories()
            }
        }
    }

    private fun fetchUsersAndRepositories() = with(binding) {
        hideKeyboard()
        etSearch.clearFocus()
        etSearch.isEnabled = false
        sivSearch.isEnabled = false
        viewModel.searchUsersAndRepositories(etSearch.text.toString())
    }

    override fun launchObservers() {
        spectateUsersAndRepositories()
    }

    private fun spectateUsersAndRepositories() = with(binding) {
        viewModel.userAndRepositories.spectateUIStateReactively(success = {
            userAndRepositoriesAdapter.submitList(it)
        }, error = {
            iErrorTryAgain.tvError.text = it
        }, onStateCollected = {
            iErrorTryAgain.root.isGone = it !is UIState.Error
            etSearch.isEnabled = it !is UIState.Loading
            sivSearch.isEnabled = it !is UIState.Loading
            rvUsersAndRepositories.isVisible = it !is UIState.Loading
            cpiMain.isVisible = it is UIState.Loading
        })
    }

    private fun onUserClick(url: String) {
        val intent = Intent(Intent.ACTION_VIEW).apply {
            data = Uri.parse(url)
            addCategory(Intent.CATEGORY_BROWSABLE)
        }

        val chooser = Intent.createChooser(intent, "Choose a browser")

        try {
            startActivity(chooser)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                requireContext(),
                "No browser to pick from available",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun onRepositoryClick(repository: GitHubDataUI.GitHubRepositoryUI) {
        findNavController().navigate(
            MainFragmentDirections.toRepositoryDetailsFragment(
                RepositoryDetails(repository.owner.login, repository.name, "")
            )
        )
    }
}