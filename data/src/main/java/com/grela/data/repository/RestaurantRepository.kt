package com.grela.data.repository

import com.grela.data.datasource.DoNotCookRemoteDataSourceContract
import com.grela.data.model.toCountryDomainEntityList
import com.grela.data.model.toRestaurantDomainEntityList
import com.grela.domain.DataResult
import com.grela.domain.RestaurantRepositoryContract
import com.grela.domain.model.CountryDomainEntity
import com.grela.domain.model.RestaurantDomainEntity

class RestaurantRepository(
    private val doNotCookRemoteDataSource: DoNotCookRemoteDataSourceContract
) : RestaurantRepositoryContract {
    override fun getRestaurants(): DataResult<Error, List<RestaurantDomainEntity>> {
        return when (val result = doNotCookRemoteDataSource.getRestaurants()) {
            is DataResult.Error -> result
            is DataResult.Success -> DataResult.Success(result.r.toRestaurantDomainEntityList())
        }
    }
}