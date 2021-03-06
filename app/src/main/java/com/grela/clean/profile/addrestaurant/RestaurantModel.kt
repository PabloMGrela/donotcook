package com.grela.clean.profile.addrestaurant

import com.grela.domain.interactor.restaurants.RestaurantDomainModel

data class RestaurantModel(
    val name: String,
    val address: String,
    val latitude: Float,
    val longitude: Float,
    val rating: Float,
    val phone: String,
    var logo: String?,
    var header: String?
)

fun RestaurantModel.toRestaurantDomainModel() = RestaurantDomainModel(
    name, address, latitude, longitude, rating, phone, logo, header
)