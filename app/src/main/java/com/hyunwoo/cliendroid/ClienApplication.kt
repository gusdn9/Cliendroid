package com.hyunwoo.cliendroid

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class ClienApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> = AppComponent.create(this)
}
