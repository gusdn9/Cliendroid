package com.hyunwoo.cliendroid.presentation.fragment.search.type

import com.hyunwoo.cliendroid.architecture.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchTypeFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchTypeFragment(): SearchTypeFragment
}
