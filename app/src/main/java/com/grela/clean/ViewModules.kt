package com.grela.clean

import com.grela.clean.mainlist.HomeViewModel
import com.grela.clean.profile.ProfileViewModel
import com.grela.clean.profile.addrestaurant.AddRestaurantViewModel
import com.grela.clean.profile.managerestaurants.ManageRestaurantsViewModel
import com.grela.clean.splash.SplashViewModel
import com.grela.domain.interactor.restaurants.CreateRestaurantUseCase
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModules {

    private val IO = Dispatchers.IO

    val modules = module {
        viewModel { SplashViewModel() }
        viewModel { HomeViewModel(get()) }
        viewModel { ProfileViewModel(get(), get(), get(), get(), IO) }
        viewModel { ManageRestaurantsViewModel(get(), IO) }
        viewModel { AddRestaurantViewModel(get(), IO) }
    }
}