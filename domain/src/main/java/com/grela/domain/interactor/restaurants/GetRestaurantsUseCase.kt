package com.grela.domain.interactor.restaurants

import com.grela.domain.RestaurantRepositoryContract
import com.grela.domain.interactor.Callback
import com.grela.domain.interactor.UseCase
import com.grela.domain.model.RestaurantDomainEntity


class GetRestaurantsUseCase(private val repository: RestaurantRepositoryContract) :
    UseCase<List<RestaurantDomainEntity>>() {
    override suspend fun run(listener: Callback<Error, List<RestaurantDomainEntity>>) {
        listener.notify(repository.getRestaurants())
    }

}
