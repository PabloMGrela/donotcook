package com.grela.clean.profile.addrestaurant

data class RestaurantModel(
    val name: String,
    val address: String,
    val latitude: Float,
    val longitude: Float,
    val rating: Float,
    val phone: String

)