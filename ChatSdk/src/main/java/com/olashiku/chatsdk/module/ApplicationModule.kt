package com.olashiku.chatsdk.module

import androidx.multidex.MultiDexApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class ApplicationModule: MultiDexApplication() {
    override fun onCreate(){
        super.onCreate()
        startKoin {
            androidContext(this@ApplicationModule)
            modules(listOf(network,repository,viewModel,storage))
        }
    }

}