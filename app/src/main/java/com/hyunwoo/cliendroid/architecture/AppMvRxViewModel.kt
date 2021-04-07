package com.hyunwoo.cliendroid.architecture

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.Fail
import com.airbnb.mvrx.Loading
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.MavericksViewModel
import com.airbnb.mvrx.Success

abstract class AppMvRxViewModel<S : MavericksState>(
    initialState: S
) : MavericksViewModel<S>(initialState) {

    suspend fun <V> (suspend () -> V).asAsync(reducer: S.(Async<V>) -> S) {
        setState { reducer(Loading()) }
        try {
            val value = this()
            setState { reducer(Success(value)) }
        } catch (throwable: Throwable) {
            setState { reducer(Fail(throwable)) }
        }
    }

    suspend fun <A, V> (suspend (A) -> V).asAsync(
        arg1: A,
        reducer: S.(Async<V>) -> S
    ) = suspend { this(arg1) }.asAsync(reducer)

    suspend fun <A, B, V> (suspend (A, B) -> V).asAsync(
        arg1: A,
        arg2: B,
        reducer: S.(Async<V>) -> S
    ) = suspend { this(arg1, arg2) }.asAsync(reducer)

    suspend fun <A, B, C, V> (suspend (A, B, C) -> V).asAsync(
        arg1: A,
        arg2: B,
        arg3: C,
        reducer: S.(Async<V>) -> S
    ) = suspend { this(arg1, arg2, arg3) }.asAsync(reducer)

    suspend fun <A, B, C, D, V> (suspend (A, B, C, D) -> V).asAsync(
        arg1: A,
        arg2: B,
        arg3: C,
        arg4: D,
        reducer: S.(Async<V>) -> S
    ) = suspend { this(arg1, arg2, arg3, arg4) }.asAsync(reducer)

    suspend fun <A, B, C, D, E, V> (suspend (A, B, C, D, E) -> V).asAsync(
        arg1: A,
        arg2: B,
        arg3: C,
        arg4: D,
        arg5: E,
        reducer: S.(Async<V>) -> S
    ) = suspend { this(arg1, arg2, arg3, arg4, arg5) }.asAsync(reducer)
}
