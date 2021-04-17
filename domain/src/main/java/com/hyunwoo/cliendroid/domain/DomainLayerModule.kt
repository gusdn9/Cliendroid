package com.hyunwoo.cliendroid.domain

import com.hyunwoo.cliendroid.domain.event.EventChannel
import com.hyunwoo.cliendroid.domain.event.LoggedInEvent
import com.hyunwoo.cliendroid.domain.event.LoggedInUserUpdatedEvent
import com.hyunwoo.cliendroid.domain.event.LoggedOutEvent
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [AssistedInjectModule::class])
class DomainLayerModule {

    @Named("LoggedInChannel")
    @Singleton
    @Provides
    fun provideLoggedInChannel(@Named("ApplicationScope") scope: CoroutineScope) =
        EventChannel<LoggedInEvent>(scope)

    @Named("LoggedInUserUpdatedChannel")
    @Singleton
    @Provides
    fun provideLoggedInUserUpdatedChannel(@Named("ApplicationScope") scope: CoroutineScope) =
        EventChannel<LoggedInUserUpdatedEvent>(scope)

    @Named("LoggedOutChannel")
    @Singleton
    @Provides
    fun provideLoggedOutChannel(@Named("ApplicationScope") scope: CoroutineScope) =
        EventChannel<LoggedOutEvent>(scope)
}
