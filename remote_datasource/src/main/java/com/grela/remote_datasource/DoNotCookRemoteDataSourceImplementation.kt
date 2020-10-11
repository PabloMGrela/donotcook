package com.grela.remote_datasource

import com.grela.data.datasource.DoNotCookRemoteDataSourceContract
import com.grela.data.model.RestaurantDataEntity
import com.grela.domain.DataResult
import com.grela.remote_datasource.model.toRestaurantDataEntityList
import org.koin.core.KoinComponent
import org.koin.core.inject

class DoNotCookRemoteDataSourceImplementation : DoNotCookRemoteDataSourceContract, KoinComponent {

    private val api: StrapiAPI by inject()

    override fun getRestaurants(): DataResult<Error, List<RestaurantDataEntity>> {
        return safeCall({ api.getRestaurants() }, { list -> list.toRestaurantDataEntityList() })
    }
}