package com.grela.data.model

import com.grela.domain.model.MenuDomainEntity
import com.grela.domain.model.RestaurantDomainEntity
import com.grela.domain.model.SectionDomainEntity

data class RestaurantDataEntity(
    val id: Int,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Float,
    val logo: String,
    val image: String,
    val phone: Int,
    val address: String,
    val menus: List<MenuDataEntity>,
    val isFav: Boolean
)

data class MenuDataEntity(
    val id: Int,
    val price: Float,
    val dessertAndCoffee: Boolean,
    val sections: List<SectionDataEntity>
)

data class SectionDataEntity(
    val title: String,
    val values: List<String>
)


fun SectionDataEntity.toSectionDomainEntity() =
    SectionDomainEntity(title, values)

fun List<SectionDataEntity>.toSectionDomainEntityList() = map { it.toSectionDomainEntity() }

fun MenuDataEntity.toMenuDomainEntity() =
    MenuDomainEntity(id, price, dessertAndCoffee, sections.toSectionDomainEntityList())

fun List<MenuDataEntity>.toMenuDomainEntityList() = map { it.toMenuDomainEntity() }

fun RestaurantDataEntity.toRestaurantDomainEntity() = RestaurantDomainEntity(
    id,
    name,
    latitude,
    longitude,
    rating,
    logo,
    image,
    phone,
    address,
    menus.toMenuDomainEntityList(),
    isFav
)

fun List<RestaurantDataEntity>.toRestaurantDomainEntityList() =
    map { it.toRestaurantDomainEntity() }