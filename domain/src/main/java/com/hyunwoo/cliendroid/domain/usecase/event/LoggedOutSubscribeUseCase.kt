package com.hyunwoo.cliendroid.domain.usecase.event

import com.hyunwoo.cliendroid.domain.event.EventChannel
import com.hyunwoo.cliendroid.domain.event.LoggedOutEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class LoggedOutSubscribeUseCase @Inject constructor(
    @Named("LoggedOutChannel")
    private val channel: EventChannel<LoggedOutEvent>
) {

    operator fun invoke(): Flow<LoggedOutEvent> = channel.asFlow()
}
