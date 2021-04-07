package com.hyunwoo.cliendroid.presentation.activity.main

import com.hyunwoo.cliendroid.architecture.ActivityScope
import com.hyunwoo.cliendroid.presentation.fragment.everyonepark.EveryOneParkFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            EveryOneParkFragmentBuilderModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}

@Module
class MainActivityModule
