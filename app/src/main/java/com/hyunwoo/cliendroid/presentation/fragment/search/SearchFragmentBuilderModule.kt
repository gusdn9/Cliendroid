package com.hyunwoo.cliendroid.presentation.fragment.search

import com.hyunwoo.cliendroid.presentation.fragment.search.board.SearchBoardFragmentBuilderModule
import com.hyunwoo.cliendroid.presentation.fragment.search.type.SearchTypeFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(
    includes = [
        SearchBoardFragmentBuilderModule::class,
        SearchTypeFragmentBuilderModule::class
    ]
)
abstract class SearchFragmentBuilderModule {

    @SearchFragmentScope
    @ContributesAndroidInjector
    abstract fun contributeSearchFragment(): SearchFragment
}
