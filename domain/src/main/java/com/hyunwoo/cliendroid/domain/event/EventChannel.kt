package com.hyunwoo.cliendroid.domain.event

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.launch

@kotlinx.coroutines.ExperimentalCoroutinesApi
class EventChannel<T>(
    private val scope: CoroutineScope
) {

    private val channel = BroadcastChannel<T>(1)

    fun send(event: T) {
        scope.launch {
            channel.send(event)
        }
    }

    @FlowPreview
    fun asFlow(): Flow<T> = channel.asFlow()
}
