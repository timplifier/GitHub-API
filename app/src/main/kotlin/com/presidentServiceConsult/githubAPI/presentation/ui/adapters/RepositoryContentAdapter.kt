package com.presidentServiceConsult.githubAPI.presentation.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.presidentServiceConsult.githubAPI.R
import com.presidentServiceConsult.githubAPI.databinding.ItemRepositoryContentBinding
import com.presidentServiceConsult.githubAPI.presentation.foundation.DiffUtil
import com.presidentServiceConsult.githubAPI.presentation.models.GitHubRepositoryContentUI

class RepositoryContentAdapter(private val onContentClick: (GitHubRepositoryContentUI) -> Unit) :
    ListAdapter<GitHubRepositoryContentUI, RepositoryContentAdapter.RepositoryContentViewHolder>(
        DiffUtil()
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryContentViewHolder {
        return RepositoryContentViewHolder(
            ItemRepositoryContentBinding.inflate(
                LayoutInflater.from(
                    parent.context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryContentViewHolder, position: Int) {
        getItem(position)?.let { holder.onBind(it) }
    }

    inner class RepositoryContentViewHolder(private val binding: ItemRepositoryContentBinding) :
        ViewHolder(binding.root) {
        fun onBind(repositoryContent: GitHubRepositoryContentUI) {
            binding.sivIcon.setImageResource(
                when (repositoryContent.type) {
                    "dir" -> R.drawable.ic_folder
                    else -> R.drawable.ic_file
                }
            )
            binding.tvName.text = repositoryContent.name
        }

        init {
            binding.root.setOnClickListener {
                onContentClick(getItem(adapterPosition))
            }
        }
    }
}