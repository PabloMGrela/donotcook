package com.grela.remote_datasource

import com.grela.domain.DataResult
import com.grela.remote_datasource.model.RestaurantRemoteEntity
import retrofit2.Call
import retrofit2.http.GET
import java.io.IOException

interface StrapiAPI {

    companion object {
        val baseUrl = "https://donotcook.herokuapp.com/"
    }

    @GET("restaurants")
    fun getRestaurants(): Call<List<RestaurantRemoteEntity>>

}

inline fun <T, R> safeCall(
    call: () -> Call<R>,
    transform: (R) -> T
): DataResult<Error, T> =
    try {
        val result = call()
        val response = result.execute()
        if (response.isSuccessful) {
            response.body()?.let {
                DataResult.Success(transform(it))
            } ?: DataResult.Error(Error())
        } else {
            DataResult.Error(Error())
        }
    } catch (exception: IOException) {
        DataResult.Error(Error())
    }