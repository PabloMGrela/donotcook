package com.grela.clean

import com.grela.clean.mainlist.HomeViewModel
import com.grela.clean.profile.ProfileViewModel
import com.grela.clean.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModules {

    val modules = module {
        viewModel { SplashViewModel() }
        viewModel { HomeViewModel(get()) }
        viewModel { ProfileViewModel(get(), get()) }
    }
}