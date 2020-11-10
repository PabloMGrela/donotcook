package com.grela.presentation

import com.grela.domain.DataResult
import com.grela.domain.interactor.restaurants.GetRestaurantsUseCase
import com.grela.domain.interactor.Invoker
import com.grela.domain.model.RestaurantDomainEntity

class MainPresenter(
    private val view: MainViewTranslator,
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
    private val invoker: Invoker
) : BasePresenter() {

    fun onCreate() {
        getCountries()
    }

    private fun getCountries() {
        invoker.execute(getRestaurantsUseCase) {
            when (it) {
                is DataResult.Success -> view.showRestaurants(it.r)
                else -> view.showError()

            }
        }
    }
}

interface MainViewTranslator {
    fun showError()
    fun showRestaurants(r: List<RestaurantDomainEntity>)
}