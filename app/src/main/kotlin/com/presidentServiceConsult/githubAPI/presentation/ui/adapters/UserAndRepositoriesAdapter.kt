package com.presidentServiceConsult.githubAPI.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import coil.load
import com.presidentServiceConsult.githubAPI.R
import com.presidentServiceConsult.githubAPI.databinding.ItemRepositoryBinding
import com.presidentServiceConsult.githubAPI.databinding.ItemUserBinding
import com.presidentServiceConsult.githubAPI.presentation.foundation.DiffUtil
import com.presidentServiceConsult.githubAPI.presentation.models.GitHubDataUI

class UserAndRepositoriesAdapter(
    private val onUserClick: (url: String) -> Unit,
    private val onRepositoryClick: (repository: GitHubDataUI.GitHubRepositoryUI) -> Unit
) :
    ListAdapter<GitHubDataUI, ViewHolder>(DiffUtil()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.item_user -> {
                UserViewHolder(ItemUserBinding.inflate(inflater, parent, false))
            }

            R.layout.item_repository -> {
                RepositoryViewHolder(ItemRepositoryBinding.inflate(inflater, parent, false))
            }

            else -> throw IllegalArgumentException("Unknown item viewType $viewType")
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let {
            when (getItemViewType(position)) {
                R.layout.item_user -> (holder as UserViewHolder).onBind(it as GitHubDataUI.GitHubUserUI)
                R.layout.item_repository -> (holder as RepositoryViewHolder).onBind(it as GitHubDataUI.GitHubRepositoryUI)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is GitHubDataUI.GitHubRepositoryUI -> R.layout.item_repository
            is GitHubDataUI.GitHubUserUI -> R.layout.item_user
        }
    }

    private inner class UserViewHolder(val binding: ItemUserBinding) : ViewHolder(binding.root) {
        fun onBind(user: GitHubDataUI.GitHubUserUI) {
            binding.sivAvatar.load(user.avatarUrl) {
                error(R.drawable.ic_launcher_foreground)
            }
            binding.tvLogin.text = user.login
            binding.tvScore.text = user.score.toString()
        }

        init {
            binding.root.setOnClickListener {
                onUserClick((getItem(adapterPosition) as GitHubDataUI.GitHubUserUI).htmlUrl)
            }
        }
    }

    private inner class RepositoryViewHolder(val binding: ItemRepositoryBinding) :
        ViewHolder(binding.root) {

        fun onBind(repository: GitHubDataUI.GitHubRepositoryUI) = with(binding) {
            tvTitle.text = repository.name
            tvForks.text =
                root.context.getString(R.string.forks_count, repository.forksCount.toString())
            tvDescription.text = repository.description
        }

        init {
            binding.root.setOnClickListener {
                onRepositoryClick((getItem(adapterPosition) as GitHubDataUI.GitHubRepositoryUI))
            }
        }
    }
}