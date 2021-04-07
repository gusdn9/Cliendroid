package com.hyunwoo.cliendroid

import com.hyunwoo.cliendroid.data.DataLayerModule
import com.hyunwoo.cliendroid.domain.DomainLayerModule
import com.hyunwoo.cliendroid.presentation.PresentationModule
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Named
import javax.inject.Singleton

@Module(
    includes = [
        PresentationModule::class,
        DomainLayerModule::class,
        DataLayerModule::class
    ]
)
class AppModule {

    @Named("ApplicationScope")
    @Provides
    @Singleton
    fun provideApplicationScope(): CoroutineScope =
        CoroutineScope(Dispatchers.Main + CoroutineName("ApplicationContext") + SupervisorJob())
}
