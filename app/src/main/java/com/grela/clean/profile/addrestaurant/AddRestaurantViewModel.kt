package com.grela.clean.profile.addrestaurant

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grela.domain.DataResult
import com.grela.domain.interactor.restaurants.CreateRestaurantUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class AddRestaurantViewModel(
    private val createRestaurantUseCase: CreateRestaurantUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success


    fun onCreateRestaurantClicked(restaurantModel: RestaurantModel) {
        viewModelScope.launch(dispatcher) {
            val result = createRestaurantUseCase.execute(restaurantModel.toRestaurantDomainModel())
            when (result) {
                is DataResult.Success -> _success.postValue(true)
                is DataResult.Error -> _success.postValue(false)
            }
        }
    }

}