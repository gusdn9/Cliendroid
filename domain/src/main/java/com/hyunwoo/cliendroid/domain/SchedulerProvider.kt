package com.hyunwoo.cliendroid.domain

import kotlinx.coroutines.CoroutineScope

interface SchedulerProvider {

    val mainThread: CoroutineScope

    val io: CoroutineScope

    val default: CoroutineScope
}
