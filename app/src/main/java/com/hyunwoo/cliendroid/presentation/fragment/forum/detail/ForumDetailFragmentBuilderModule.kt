package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import com.hyunwoo.cliendroid.architecture.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ForumDetailFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeForumDetailFragment(): ForumDetailFragment
}
