package com.hyunwoo.cliendroid.presentation.fragment.search

import com.hyunwoo.cliendroid.architecture.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}
