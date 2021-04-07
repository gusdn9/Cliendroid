package com.hyunwoo.cliendroid.presentation.fragment.everyonepark

import com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list.EveryOneParkListFragmentBuilderModule
import dagger.Module

@Module(
    includes = [
        EveryOneParkListFragmentBuilderModule::class
    ]
)
class EveryOneParkFragmentBuilderModule
