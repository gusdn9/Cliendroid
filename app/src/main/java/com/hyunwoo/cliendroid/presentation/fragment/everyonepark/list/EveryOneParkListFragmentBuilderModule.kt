package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.hyunwoo.cliendroid.architecture.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EveryOneParkListFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeEveryOneParkListFragment(): EveryOneParkListFragment
}
