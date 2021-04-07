package com.hyunwoo.cliendroid.presentation

import com.hyunwoo.cliendroid.presentation.activity.main.MainActivityBuilderModule
import dagger.Module

@Module(
    includes = [
        MainActivityBuilderModule::class
    ]
)
class PresentationModule
