package com.hyunwoo.cliendroid.presentation.fragment.forum

import com.hyunwoo.cliendroid.presentation.fragment.forum.detail.ForumDetailFragmentBuilderModule
import com.hyunwoo.cliendroid.presentation.fragment.forum.list.ForumListFragmentBuilderModule
import dagger.Module

@Module(
    includes = [
        ForumListFragmentBuilderModule::class,
        ForumDetailFragmentBuilderModule::class
    ]
)
class ForumFragmentBuilderModule
