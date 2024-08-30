package com.presidentServiceConsult.githubAPI.domain

import com.presidentServiceConsult.githubAPI.domain.useCases.GetRepositoryContentsUseCase
import com.presidentServiceConsult.githubAPI.domain.useCases.SearchUsersAndRepositoriesUseCase
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetRepositoryContentsUseCase)
    factoryOf(::SearchUsersAndRepositoriesUseCase)
}