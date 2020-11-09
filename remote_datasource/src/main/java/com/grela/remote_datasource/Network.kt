package com.grela.remote_datasource

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.google.gson.GsonBuilder
import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.core.KoinComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Network() : KoinComponent {

    companion object {
        private var networkFlipperPlugin = NetworkFlipperPlugin()
        fun getNetworkFlipperPlugin() = networkFlipperPlugin
    }

    fun <T> provideApi(
        baseUrl: String,
        clazz: Class<T>
    ): T =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                createOkHttpClient(
                    null,
                    listOf(
                        FlipperOkhttpInterceptor(
                            networkFlipperPlugin
                        )
                    )
                )
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(clazz)

    private fun createOkHttpClient(
        auth: Authenticator?,
        interceptors: List<Interceptor>
    ): OkHttpClient = try {
        OkHttpClient.Builder().apply {
            for (interceptor in interceptors) {
                addInterceptor(interceptor)
            }
            auth?.let {
                authenticator(it)
            }
        }.build()
    } catch (e: Exception) {
        throw RuntimeException(e)
    }

}