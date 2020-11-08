package com.grela.clean

import android.app.Application
import com.facebook.flipper.android.AndroidFlipperClient
import com.facebook.flipper.plugins.inspector.DescriptorMapping
import com.facebook.flipper.plugins.inspector.InspectorFlipperPlugin
import com.facebook.soloader.SoLoader
import com.google.android.libraries.places.api.Places
import com.grela.remote_datasource.Network
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.logger.EmptyLogger

class CleanApplication : Application() {
    companion object {
        private var mInstance: CleanApplication? = null

        fun instance(): CleanApplication? {
            return mInstance
        }

    }

    override fun onCreate() {
        super.onCreate()
        if (Places.isInitialized().not()) {
            Places.initialize(this, getString(R.string.places_key))
        }
        SoLoader.init(this, false)

        if (BuildConfig.DEBUG) {
            val client = AndroidFlipperClient.getInstance(this)
            client.addPlugin(InspectorFlipperPlugin(this, DescriptorMapping.withDefaults()))
            client.addPlugin(Network.getNetworkFlipperPlugin())
            client.start()
        }
        startKoin {
            androidContext(this@CleanApplication)
            modules(AppModules.modules)
            EmptyLogger()
        }
    }

}