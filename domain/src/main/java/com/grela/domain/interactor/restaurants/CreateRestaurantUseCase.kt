package com.grela.domain.interactor.restaurants

import com.grela.domain.DataResult
import com.grela.domain.RestaurantRepositoryContract

class CreateRestaurantUseCase (private val repository: RestaurantRepositoryContract){
    fun execute(restaurant: RestaurantDomainModel): DataResult<Error, Any> {
        return repository.createRestaurant(restaurant)
    }
}