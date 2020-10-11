package com.grela.clean.mainlist

import com.google.android.libraries.maps.model.LatLng
import com.grela.clean.getDistanceString
import com.grela.domain.model.MenuDomainEntity
import com.grela.domain.model.RestaurantDomainEntity
import com.grela.domain.model.SectionDomainEntity
import java.io.Serializable

data class RestaurantViewModel(
    val name: String,
    val logo: String,
    val image: String,
    val price: Float,
    val distance: String,
    val rating: Float,
    val phone: Int,
    val address: String,
    val menus: List<MenuViewModel>
) : Serializable

data class MenuViewModel(
    val price: Float,
    val sections: List<SectionViewModel>
) : Serializable

data class SectionViewModel(
    val name: String,
    val options: List<String>
) : Serializable

fun SectionDomainEntity.toSectionViewModelEntity() =
    SectionViewModel(title, values)

fun List<SectionDomainEntity>.toSectionViewModelEntityList() = map { it.toSectionViewModelEntity() }

fun MenuDomainEntity.toMenuViewModelEntity() =
    MenuViewModel(price, sections.toSectionViewModelEntityList())

fun List<MenuDomainEntity>.toMenuViewModelEntityList() =
    map { it.toMenuViewModelEntity() }

fun RestaurantDomainEntity.toRestaurantViewModelEntity(currentLocation: LatLng) =
    RestaurantViewModel(
        name,
        logo,
        image,
        menus.firstOrNull()?.price ?: 0f,
        currentLocation.getDistanceString(LatLng(latitude, longitude)),
        rating,
        phone,
        address,
        menus.toMenuViewModelEntityList()
    )

fun List<RestaurantDomainEntity>.toRestaurantViewModelEntityList(currentLocation: LatLng) =
    map { it.toRestaurantViewModelEntity(currentLocation) }