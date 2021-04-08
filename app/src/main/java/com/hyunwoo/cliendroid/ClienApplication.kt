package com.hyunwoo.cliendroid

import com.airbnb.mvrx.Mavericks
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class ClienApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = AppComponent.create(this)

    override fun onCreate() {
        super.onCreate()
        Mavericks.initialize(this)
    }
}
