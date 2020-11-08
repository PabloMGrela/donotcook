package com.grela.remote_datasource

import com.facebook.flipper.plugins.network.FlipperOkhttpInterceptor
import com.facebook.flipper.plugins.network.NetworkFlipperPlugin
import com.google.gson.GsonBuilder
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
                OkHttpClient.Builder().addNetworkInterceptor(
                    FlipperOkhttpInterceptor(
                        networkFlipperPlugin
                    )
                ).build()
            )
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(clazz)

}