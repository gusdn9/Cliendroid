package com.hyunwoo.cliendroid.presentation.fragment.everyonepark

import com.hyunwoo.cliendroid.presentation.fragment.everyonepark.detail.EveryoneParkDetailFragmentBuilderModule
import com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list.EveryoneParkListFragmentBuilderModule
import dagger.Module

@Module(
    includes = [
        EveryoneParkListFragmentBuilderModule::class,
        EveryoneParkDetailFragmentBuilderModule::class
    ]
)
class EveryoneParkFragmentBuilderModule
