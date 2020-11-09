package com.grela.clean.profile.managerestaurants

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grela.clean.mainlist.RestaurantViewModel
import com.grela.clean.mainlist.toRestaurantViewModelEntityList
import com.grela.domain.DataResult
import com.grela.domain.interactor.profile.GetOwnRestaurantsUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class ManageRestaurantsViewModel(
    private val getOwnRestaurantsUseCase: GetOwnRestaurantsUseCase,
    private val dispatcher: CoroutineDispatcher
) : ViewModel() {

    private val _restaurantList = MutableLiveData<List<RestaurantViewModel>>()
    val restaurantList: LiveData<List<RestaurantViewModel>> = _restaurantList

    fun onViewCreated() {
        viewModelScope.launch(dispatcher) {
            val result = getOwnRestaurantsUseCase.execute()
            if (result is DataResult.Success) {
                _restaurantList.postValue(result.r.toRestaurantViewModelEntityList(null))
            }
        }
    }
}