package com.grela.data.datasource

import com.grela.data.model.RestaurantDataEntity
import com.grela.domain.DataResult
import com.grela.domain.model.ProfileGeneralModel

interface DoNotCookRemoteDataSourceContract {
    fun getRestaurants(): DataResult<Error, List<RestaurantDataEntity>>

    fun login(username: String, pass: String): DataResult<Error, ProfileGeneralModel>
}