package com.hyunwoo.cliendroid.domain.usecase.event

import com.hyunwoo.cliendroid.domain.event.EventChannel
import com.hyunwoo.cliendroid.domain.event.LoggedInEvent
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Named

class LoggedInSubscribeUseCase @Inject constructor(
    @Named("LoggedInChannel")
    private val channel: EventChannel<LoggedInEvent>
) {

    operator fun invoke(): Flow<LoggedInEvent> = channel.asFlow()
}
