package com.hyunwoo.cliendroid.presentation.activity.main

import com.hyunwoo.cliendroid.architecture.ActivityScope
import com.hyunwoo.cliendroid.presentation.fragment.drawer.DrawerFragmentBuilderModule
import com.hyunwoo.cliendroid.presentation.fragment.forum.ForumFragmentBuilderModule
import com.hyunwoo.cliendroid.presentation.fragment.search.board.SearchBoardFragmentBuilderModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilderModule {

    @ActivityScope
    @ContributesAndroidInjector(
        modules = [
            MainActivityModule::class,
            DrawerFragmentBuilderModule::class,
            ForumFragmentBuilderModule::class,
            SearchBoardFragmentBuilderModule::class
        ]
    )
    abstract fun contributeMainActivity(): MainActivity
}

@Module
class MainActivityModule
