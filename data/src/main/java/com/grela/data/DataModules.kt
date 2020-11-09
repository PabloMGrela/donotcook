package com.grela.data

import com.grela.data.repository.RestaurantRepository
import com.grela.domain.RestaurantRepositoryContract
import org.koin.dsl.module

object DataModules {

    val modules = module {
        factory<RestaurantRepositoryContract> { RestaurantRepository(get(), get()) }
    }
}