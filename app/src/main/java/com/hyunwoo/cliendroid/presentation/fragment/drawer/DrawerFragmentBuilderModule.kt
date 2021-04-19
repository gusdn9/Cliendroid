package com.hyunwoo.cliendroid.presentation.fragment.drawer

import com.hyunwoo.cliendroid.architecture.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class DrawerFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeDrawerFragment(): DrawerFragment
}
