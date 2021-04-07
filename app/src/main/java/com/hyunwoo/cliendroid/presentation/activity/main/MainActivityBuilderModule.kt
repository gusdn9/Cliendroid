package com.hyunwoo.cliendroid.presentation.activity.main

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilderModule {

    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}

@Module
class MainActivityModule
