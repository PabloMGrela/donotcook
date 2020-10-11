package com.grela.domain.interactor

import com.grela.domain.RestaurantRepositoryContract
import com.grela.domain.model.RestaurantDomainEntity


class GetRestaurantsUseCase(private val repository: RestaurantRepositoryContract) :
    UseCase<List<RestaurantDomainEntity>>() {
    override suspend fun run(listener: Callback<Error, List<RestaurantDomainEntity>>) {
        listener.notify(repository.getRestaurants())
    }

}
