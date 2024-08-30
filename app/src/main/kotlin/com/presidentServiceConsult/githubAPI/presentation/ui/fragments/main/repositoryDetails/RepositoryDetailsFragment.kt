package com.presidentServiceConsult.githubAPI.presentation.ui.fragments.main.repositoryDetails

import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.presidentServiceConsult.githubAPI.R
import com.presidentServiceConsult.githubAPI.databinding.FragmentRepositoryDetailsBinding
import com.presidentServiceConsult.githubAPI.presentation.foundation.Fragment
import com.presidentServiceConsult.githubAPI.presentation.foundation.UIState
import com.presidentServiceConsult.githubAPI.presentation.models.GitHubRepositoryContentUI
import com.presidentServiceConsult.githubAPI.presentation.ui.adapters.RepositoryContentAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoryDetailsFragment :
    Fragment<FragmentRepositoryDetailsBinding, RepositoryDetailsViewModel>(
        R.layout.fragment_repository_details
    ) {

    override val binding by viewBinding(FragmentRepositoryDetailsBinding::bind)
    override val viewModel by viewModel<RepositoryDetailsViewModel>()
    private val args by navArgs<RepositoryDetailsFragmentArgs>()
    private val adapter by lazy { RepositoryContentAdapter(::onContentClick) }

    override fun assembleViews() {
        binding.rvContent.constructRecyclerView(adapter) {
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    override fun bindListeners() {
        bindTryAgain()
    }

    private fun bindTryAgain() {
        binding.iErrorTryAgain.btnTryAgain.setOnClickListener {
            fetchRepositoryContents()
        }
    }

    override fun launchObservers() {
        spectateRepositoryContents()
    }

    override fun establishRequest() {
        fetchRepositoryContents()
    }

    private fun fetchRepositoryContents() {
        viewModel.getRepositoryContents(
            args.repositoryDetails.repositoryOwner,
            args.repositoryDetails.repositoryName,
            args.repositoryDetails.repositoryPath
        )
    }

    private fun spectateRepositoryContents() {
        viewModel.repositoryContents.spectateUIStateReactively(success = {
            adapter.submitList(it)
        }, error = {
            binding.iErrorTryAgain.tvError.text = it
        }, onStateCollected = {
            binding.iErrorTryAgain.root.isGone = it !is UIState.Error
            binding.cpiRepositoryDetails.isVisible = it is UIState.Loading
        })
    }

    private fun onContentClick(content: GitHubRepositoryContentUI) {
        when (content.type) {
            "dir" ->
                findNavController().navigate(
                    RepositoryDetailsFragmentDirections.toRepositoryDetailsFragment(
                        args.repositoryDetails.copy(repositoryPath = content.path)
                    )
                )

            "file" ->
                CustomTabsIntent.Builder()
                    .setUrlBarHidingEnabled(true)
                    .setShowTitle(true)
                    .build()
                    .launchUrl(requireContext(), Uri.parse(content.htmlUrl))
        }
    }
}