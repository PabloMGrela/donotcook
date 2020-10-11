package com.grela.data.datasource

import com.grela.data.model.CountryDataEntity
import com.grela.data.model.RestaurantDataEntity
import com.grela.domain.DataResult

interface DoNotCookRemoteDataSourceContract {
    fun getRestaurants(): DataResult<Error, List<RestaurantDataEntity>>
}