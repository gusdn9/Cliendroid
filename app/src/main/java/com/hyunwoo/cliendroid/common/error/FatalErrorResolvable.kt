package com.hyunwoo.cliendroid.common.error

interface FatalErrorResolvable<T : ErrorView> {

    fun T.onUnauthorized(onRetry: OnRetry? = null)
}
