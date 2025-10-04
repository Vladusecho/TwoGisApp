package com.example.twogisapp.app

import android.app.Application
import ru.dgis.sdk.Context
import ru.dgis.sdk.DGis

class MyApp : Application() {

    lateinit var sdkContext: Context

    override fun onCreate() {
        super.onCreate()

        sdkContext = DGis.initialize(this)
    }
}