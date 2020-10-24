package com.grela.domain.model

data class RestaurantDomainEntity(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Float,
    val logo: String,
    val image: String,
    val phone: Int,
    val address: String,
    val menus: List<MenuDomainEntity>,
    val isFav: Boolean
)

data class MenuDomainEntity(
    val id: Int,
    val price: Float,
    val dessertAndCoffee: Boolean,
    val sections: List<SectionDomainEntity>
)

data class SectionDomainEntity(
    val title: String,
    val values: List<String>
)