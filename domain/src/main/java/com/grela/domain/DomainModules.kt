package com.grela.domain

import com.grela.domain.interactor.GetRestaurantsUseCase
import com.grela.domain.interactor.Invoker
import com.grela.domain.interactor.UseCaseInvoker
import org.koin.dsl.module

object DomainModules {
    val modules = module {
        factory { GetRestaurantsUseCase(get()) }
        factory<Invoker> { UseCaseInvoker() }
    }
}