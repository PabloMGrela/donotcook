package com.grela.clean.mainlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grela.domain.DataResult
import com.grela.domain.interactor.restaurants.GetRestaurantsUseCase
import com.grela.domain.model.RestaurantDomainEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel(private val getRestaurantsUseCase: GetRestaurantsUseCase) : ViewModel() {

    private val _restaurantList = MutableLiveData<List<RestaurantDomainEntity>>()
    val restaurantList: LiveData<List<RestaurantDomainEntity>> = _restaurantList

    fun onCreated() {
        viewModelScope.launch(Dispatchers.IO) {
            getRestaurantsUseCase.run {
                if (it is DataResult.Success) {
                    _restaurantList.postValue(it.r)
                }
            }
        }
    }

}