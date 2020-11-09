package com.grela.domain.interactor.profile

import com.grela.domain.DataResult
import com.grela.domain.RestaurantRepositoryContract
import com.grela.domain.model.ProfileGeneralModel

class LoginUseCase(private val repository: RestaurantRepositoryContract) {
    fun execute(user: String, pass: String): DataResult<Error, ProfileGeneralModel> {
        return repository.login(user, pass)
    }
}