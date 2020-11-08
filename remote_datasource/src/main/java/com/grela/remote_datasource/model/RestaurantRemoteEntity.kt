package com.grela.remote_datasource.model

import com.google.gson.annotations.SerializedName
import com.grela.data.model.MenuDataEntity
import com.grela.data.model.RestaurantDataEntity
import com.grela.data.model.SectionDataEntity

data class RestaurantRemoteEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("rating") val rating: Float,
    @SerializedName("logo") val logo: String,
    @SerializedName("image") val image: String,
    @SerializedName("phone") val phone: Int,
    @SerializedName("address") val address: String,
    @SerializedName("menus") val menus: List<MenuRemoteEntity>,
    @SerializedName("is_favorite") val isFav: Boolean
)

data class MenuRemoteEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("price") val price: Float,
    @SerializedName("dessertAndCoffee") val dessertAndCoffee: Boolean = false,
    @SerializedName("sections") val sections: List<SectionRemoteEntity>
)

data class SectionRemoteEntity(
    @SerializedName("title") val title: String,
    @SerializedName("values") val values: List<String>
)

fun SectionRemoteEntity.toSectionDataEntity() = SectionDataEntity(title, values)

fun List<SectionRemoteEntity>.toSectionDataEntityList() = map { it.toSectionDataEntity() }

fun MenuRemoteEntity.toMenuDataEntity() =
    MenuDataEntity(id, price, dessertAndCoffee, sections.toSectionDataEntityList())

fun List<MenuRemoteEntity>.toMenuDataEntityList() = map { it.toMenuDataEntity() }

fun RestaurantRemoteEntity.toRestaurantDataEntity() = RestaurantDataEntity(
    id,
    name,
    latitude,
    longitude,
    rating,
    logo.orEmpty(),
    image.orEmpty(),
    phone,
    address,
    menus.toMenuDataEntityList(),
    isFav
)

fun List<RestaurantRemoteEntity>.toRestaurantDataEntityList() = map { it.toRestaurantDataEntity() }