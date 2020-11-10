package com.grela.domain

import com.grela.domain.interactor.Invoker
import com.grela.domain.interactor.UseCaseInvoker
import com.grela.domain.interactor.profile.GetOwnRestaurantsUseCase
import com.grela.domain.interactor.profile.GetUserUseCase
import com.grela.domain.interactor.profile.LoginUseCase
import com.grela.domain.interactor.profile.LogoutUseCase
import com.grela.domain.interactor.restaurants.CreateRestaurantUseCase
import com.grela.domain.interactor.restaurants.GetRestaurantsUseCase
import org.koin.dsl.module

object DomainModules {
    val modules = module {
        factory { GetRestaurantsUseCase(get()) }
        factory { LoginUseCase(get()) }
        factory { GetUserUseCase(get()) }
        factory { LogoutUseCase(get()) }
        factory { GetOwnRestaurantsUseCase(get()) }
        factory { CreateRestaurantUseCase(get()) }
        factory<Invoker> { UseCaseInvoker() }

    }
}