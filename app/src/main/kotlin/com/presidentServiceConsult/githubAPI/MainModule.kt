package com.presidentServiceConsult.githubAPI

import com.presidentServiceConsult.githubAPI.data.dataModule
import com.presidentServiceConsult.githubAPI.domain.domainModule
import com.presidentServiceConsult.githubAPI.presentation.ui.viewModelModule
import org.koin.dsl.module

val mainModule = module {
    includes(dataModule, domainModule, viewModelModule)
}