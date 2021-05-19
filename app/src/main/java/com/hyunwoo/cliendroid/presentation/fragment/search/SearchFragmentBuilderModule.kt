package com.hyunwoo.cliendroid.presentation.fragment.search

import com.hyunwoo.cliendroid.presentation.fragment.search.board.SearchBoardFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        SearchBoardFragmentBuilderModule::class
    ]
)
abstract class SearchFragmentBuilderModule {

    @SearchFragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}
