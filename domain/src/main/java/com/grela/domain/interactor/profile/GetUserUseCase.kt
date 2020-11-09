package com.grela.domain.interactor.profile

import com.grela.domain.DataResult
import com.grela.domain.RestaurantRepositoryContract
import com.grela.domain.model.ProfileGeneralModel

class GetUserUseCase(private val repository: RestaurantRepositoryContract) {
    fun execute(): DataResult<Error, ProfileGeneralModel>{
        return repository.getUser()
    }
}