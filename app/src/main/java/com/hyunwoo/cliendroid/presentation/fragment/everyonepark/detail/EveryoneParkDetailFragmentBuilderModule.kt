package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.detail

import com.hyunwoo.cliendroid.architecture.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class EveryoneParkDetailFragmentBuilderModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeEveryoneParkDetailFragment(): EveryoneParkDetailFragment
}
