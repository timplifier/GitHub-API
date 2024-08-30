package com.presidentServiceConsult.githubAPI.presentation.foundation

import androidx.recyclerview.widget.DiffUtil

class DiffUtil<T : DiffModel<S>, S> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}