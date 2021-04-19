package com.hyunwoo.cliendroid.presentation.activity.main

import com.hyunwoo.cliendroid.architecture.ActivityScope
import com.hyunwoo.cliendroid.presentation.fragment.drawer.DrawerFragmentBuilderModule
import com.hyunwoo.cliendroid.presentation.fragment.everyonepark.EveryoneParkFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            DrawerFragmentBuilderModule::class,
            EveryoneParkFragmentBuilderModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}

@Module
class MainActivityModule
