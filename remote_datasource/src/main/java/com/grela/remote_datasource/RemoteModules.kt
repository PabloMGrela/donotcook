package com.grela.remote_datasource

import com.grela.data.datasource.DoNotCookRemoteDataSourceContract
import org.koin.dsl.module

object RemoteModules {
    val modules = module {
        single {
            Network().provideApi(
                StrapiAPI.baseUrl,
                StrapiAPI::class.java
            )
        }
        factory<DoNotCookRemoteDataSourceContract> { DoNotCookRemoteDataSourceImplementation() }
    }
}