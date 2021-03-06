package com.grela.domain.model

data class RestaurantDomainEntity(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Float,
    val logo: String,
    val image: String,
    val phone: String,
    val address: String,
    val menus: List<MenuDomainEntity>,
    val isFav: Boolean,
    val isMine: Boolean
)

data class MenuDomainEntity(
    val id: Int,
    val name: String,
    val price: Float,
    val dessertAndCoffee: Boolean,
    val sections: List<SectionDomainEntity>,
    val date: String,
    val hasDrink: Boolean,
    val hasBread: Boolean,
    val hasDessert: Boolean
)

data class SectionDomainEntity(
    val title: String,
    val values: List<String>
)