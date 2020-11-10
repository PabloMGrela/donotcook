package com.grela.remote_datasource

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import com.grela.data.datasource.DoNotCookRemoteDataSourceContract
import com.grela.data.datasource.ImageField
import com.grela.data.model.RestaurantDataEntity
import com.grela.data.repository.RestaurantDataModel
import com.grela.domain.DataResult
import com.grela.domain.model.ProfileGeneralModel
import com.grela.remote_datasource.model.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.io.*


class DoNotCookRemoteDataSourceImplementation : DoNotCookRemoteDataSourceContract, KoinComponent {

    private val api: StrapiAPI by inject()
    private val context: Context by inject()

    override fun getRestaurants(token: String): DataResult<Error, List<RestaurantDataEntity>> {
        return safeCall(
            { api.getRestaurants(getAuthHeader(token)) },
            { list -> list.toRestaurantDataEntityList() })
    }

    override fun login(username: String, pass: String): DataResult<Error, ProfileGeneralModel> {
        return safeCall(
            { api.login(LoginRemoteEntity(username, pass)) },
            { profile -> profile.toProfileGeneralModel() })
    }

    override fun createRestaurant(
        token: String,
        toRestaurantDataModel: RestaurantDataModel
    ): DataResult<Error, RestaurantDataEntity> {
        return safeCall(
            {
                api.createRestaurant(
                    getAuthHeader(token),
                    toRestaurantDataModel.toRestaurantRemoteEntity()
                )
            }, { restaurant -> restaurant.toRestaurantDataEntity() }
        )
    }

    override fun uploadRestaurantImage(
        token: String,
        logo: String?,
        imageType: ImageField,
        restaurantRemoteEntity: RestaurantDataEntity
    ): DataResult<Error, Any> {
//        safeCall({
//            api.deleteImage(if (imageType == ImageField.LOGO) restaurantRemoteEntity.logoId else restaurantRemoteEntity.headerId)
//        }, {})
        return logo?.let {
            val bitmap = it.toBitmap()
            safeCall({
                api.uploadImage(
                    getAuthHeader(token),
                    RequestBody.create(MediaType.parse("text/plain"), "Restaurant"),
                    restaurantRemoteEntity.id,
                    RequestBody.create(MediaType.parse("text/plain"), imageType.value),
                    buildImageBodyPart(
                        if (imageType == ImageField.LOGO) "Logo ${restaurantRemoteEntity.name}" else "Header ${restaurantRemoteEntity.name}",
                        bitmap
                    )
                )
            }, {})
        } ?: DataResult.Error(Error())
    }

    private fun buildImageBodyPart(fileName: String, bitmap: Bitmap): MultipartBody.Part {
        val leftImageFile = convertBitmapToFile(fileName, bitmap)
        val reqFile = RequestBody.create(MediaType.parse("image/png"), leftImageFile)
        return MultipartBody.Part.createFormData("files", leftImageFile.name, reqFile)
    }

    private fun convertBitmapToFile(fileName: String, bitmap: Bitmap): File {
        val file = File(context.cacheDir, fileName)
        file.createNewFile()
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bos)
        val bitMapData = bos.toByteArray()
        var fos: FileOutputStream? = null
        try {
            fos = FileOutputStream(file)
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        }
        try {
            fos?.write(bitMapData)
            fos?.flush()
            fos?.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return file
    }

    private fun getAuthHeader(token: String) =
        if (token.isEmpty()) mapOf() else mapOf("Authorization" to "Bearer $token")
}

fun String.toBitmap(): Bitmap {
    val imageBytes = Base64.decode(this, 0)
    return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
}