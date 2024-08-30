package com.presidentServiceConsult.githubAPI.presentation.ui

import com.presidentServiceConsult.githubAPI.presentation.ui.fragments.main.MainViewModel
import com.presidentServiceConsult.githubAPI.presentation.ui.fragments.main.repositoryDetails.RepositoryDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::MainViewModel)
    viewModelOf(::RepositoryDetailsViewModel)
}