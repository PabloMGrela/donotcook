package com.grela.clean.mainlist

data class RestaurantViewModel(
    val name: String,
    val logo: String,
    val image: String,
    val price: Float,
    val distance: String,
    val rating: Float
)