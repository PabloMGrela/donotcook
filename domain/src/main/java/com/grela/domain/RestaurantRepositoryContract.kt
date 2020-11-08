package com.grela.domain

import com.grela.domain.model.RestaurantDomainEntity

interface RestaurantRepositoryContract {

    fun getRestaurants(): DataResult<Error, List<RestaurantDomainEntity>>
    fun login(userName: String, password: String): DataResult<Error, Any>
}