package com.hyunwoo.cliendroid.presentation.fragment.forum.list

import com.hyunwoo.cliendroid.architecture.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumListFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeForumListFragment(): ForumListFragment
}
