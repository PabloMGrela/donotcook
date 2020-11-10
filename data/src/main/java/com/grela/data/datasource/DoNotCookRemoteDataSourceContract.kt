package com.grela.data.datasource

import com.grela.data.model.RestaurantDataEntity
import com.grela.data.repository.RestaurantDataModel
import com.grela.domain.DataResult
import com.grela.domain.model.ProfileGeneralModel

interface DoNotCookRemoteDataSourceContract {
    fun getRestaurants(token: String): DataResult<Error, List<RestaurantDataEntity>>
    fun login(username: String, pass: String): DataResult<Error, ProfileGeneralModel>
    fun createRestaurant(token: String, toRestaurantDataModel: RestaurantDataModel): DataResult<Error, RestaurantDataEntity>
    fun uploadRestaurantImage(token: String, logo: String?,imageType: ImageField, restaurantRemoteEntity: RestaurantDataEntity): DataResult<Error, Any>
}

enum class ImageField(val value: String) {
    LOGO("logo_image"),
    HEADER("header_image")
}