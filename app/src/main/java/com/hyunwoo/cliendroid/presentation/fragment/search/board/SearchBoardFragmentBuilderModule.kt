package com.hyunwoo.cliendroid.presentation.fragment.search.board

import com.hyunwoo.cliendroid.architecture.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SearchBoardFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchBoardFragment
}
