package com.grela.domain.interactor

import com.grela.domain.DataResult
import com.grela.domain.RestaurantRepositoryContract

class LoginUseCase(private val repository: RestaurantRepositoryContract) {
    fun execute(user: String, pass: String): DataResult<Error, Any> {
        return repository.login(user, pass)
    }
}