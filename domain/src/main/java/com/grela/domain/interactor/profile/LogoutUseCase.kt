package com.grela.domain.interactor.profile

import com.grela.domain.RestaurantRepositoryContract

class LogoutUseCase(private val repository: RestaurantRepositoryContract) {
    fun execute(){
        repository.logout()
    }
}