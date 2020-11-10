package com.grela.remote_datasource.model

import com.google.gson.annotations.SerializedName
import com.grela.data.model.MenuDataEntity
import com.grela.data.model.RestaurantDataEntity
import com.grela.data.model.SectionDataEntity
import com.grela.data.repository.RestaurantDataModel

data class RestaurantRemoteEntity(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double,
    @SerializedName("rating") val rating: Float,
    @SerializedName("logo_image") val logo: ImageRemoteEntity?,
    @SerializedName("header_image") val image: ImageRemoteEntity?,
    @SerializedName("phone") val phone: String,
    @SerializedName("address") val address: String,
    @SerializedName("menus") val menus: List<MenuRemoteEntity>,
    @SerializedName("is_favorite") val isFav: Boolean,
    @SerializedName("is_mine") val isMine: Boolean
)

data class ImageRemoteEntity(
    @SerializedName("id") val imageId: Int,
    @SerializedName("formats") val formats: FormatsRemoteEntity
)

data class FormatsRemoteEntity(
    @SerializedName("small") val url: UrlRemoteEntity,
    @SerializedName("thumbnail") val thumbUrl: UrlRemoteEntity
)

data class UrlRemoteEntity(
    @SerializedName("url") val urlString: String
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
    logo?.formats?.thumbUrl?.urlString.orEmpty(),
    logo?.let { it.imageId } ?: 0,
    image?.formats?.url?.urlString.orEmpty(),
    image?.let { it.imageId } ?: 0,
    phone,
    address,
    menus.toMenuDataEntityList(),
    isFav,
    isMine
)

fun List<RestaurantRemoteEntity>.toRestaurantDataEntityList() = map { it.toRestaurantDataEntity() }

fun RestaurantDataModel.toRestaurantRemoteEntity() = RestaurantRemoteEntity(
    0,
    name,
    latitude.toDouble(),
    longitude.toDouble(),
    rating,
    null, null,
    phone,
    address,
    emptyList(),
    false,
    true
)