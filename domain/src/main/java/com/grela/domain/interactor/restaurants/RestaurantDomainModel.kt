package com.grela.domain.interactor.restaurants

data class RestaurantDomainModel(
    val name: String,
    val address: String,
    val latitude: Float,
    val longitude: Float,
    val rating: Float,
    val phone: String,
    var logo: String?,
    var header: String?
)

