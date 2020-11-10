package com.grela.data.repository

import com.grela.domain.interactor.restaurants.RestaurantDomainModel

data class RestaurantDataModel(
    val name: String,
    val address: String,
    val latitude: Float,
    val longitude: Float,
    val rating: Float,
    val phone: String,
    var logo: String?,
    var header: String?
)

fun RestaurantDomainModel.toRestaurantDataModel() = RestaurantDataModel(
    name, address, latitude, longitude, rating, phone, logo, header
)
