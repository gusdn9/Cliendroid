package com.hyunwoo.cliendroid.data

import com.hyunwoo.cliendroid.domain.SchedulerProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

object DefaultSchedulerProvider : SchedulerProvider {
    override val mainThread: CoroutineScope
        get() = Dispatchers.Main
    override val io: CoroutineScope
        get() = TODO("Not yet implemented")
    override val default: CoroutineScope
        get() = TODO("Not yet implemented")
}
