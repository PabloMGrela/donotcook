package com.grela.domain

import com.grela.domain.interactor.GetRestaurantsUseCase
import com.grela.domain.interactor.Invoker
import com.grela.domain.interactor.UseCaseInvoker
import com.grela.domain.interactor.profile.GetUserUseCase
import com.grela.domain.interactor.profile.LoginUseCase
import com.grela.domain.interactor.profile.LogoutUseCase
import org.koin.dsl.module

object DomainModules {
    val modules = module {
        factory { GetRestaurantsUseCase(get()) }
        factory { LoginUseCase(get()) }
        factory { GetUserUseCase(get()) }
        factory { LogoutUseCase(get()) }

        factory<Invoker> { UseCaseInvoker() }

    }
}