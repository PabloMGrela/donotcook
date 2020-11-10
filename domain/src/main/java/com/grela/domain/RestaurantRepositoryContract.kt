package com.grela.domain

import com.grela.domain.interactor.restaurants.RestaurantDomainModel
import com.grela.domain.model.ProfileGeneralModel
import com.grela.domain.model.RestaurantDomainEntity

interface RestaurantRepositoryContract {

    fun getRestaurants(): DataResult<Error, List<RestaurantDomainEntity>>
    fun login(userName: String, password: String): DataResult<Error, ProfileGeneralModel>
    fun getUser(): DataResult<Error, ProfileGeneralModel>
    fun logout()
    fun getOwnRestaurants(): DataResult<Error, List<RestaurantDomainEntity>>
    fun createRestaurant(restaurant: RestaurantDomainModel): DataResult<Error, Any>
}