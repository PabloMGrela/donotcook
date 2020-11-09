package com.grela.domain.interactor.profile

import com.grela.domain.DataResult
import com.grela.domain.RestaurantRepositoryContract
import com.grela.domain.model.RestaurantDomainEntity

class GetOwnRestaurantsUseCase(
    private val repository: RestaurantRepositoryContract
) {
    fun execute(): DataResult<Error, List<RestaurantDomainEntity>> {
        return repository.getOwnRestaurants()
    }
}