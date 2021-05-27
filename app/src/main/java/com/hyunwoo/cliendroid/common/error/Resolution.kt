package com.hyunwoo.cliendroid.common.error

import java.net.ProtocolException
import java.net.UnknownServiceException

typealias OnRetry = () -> Unit
typealias OnResolved = () -> Unit

interface Resolution<T : ErrorView> {

    /**
     * 에러를 resolution의 로직에 맞춰 resolve한다.
     *
     * @param errorView 에러를 resolve할때 사용할 view
     * @param throwable resolve할 에러
     * @param onRetry non-null일 경우 재시도할 수 있는 방법을 제공하여 해당 callback이 실행되게 한다.
     */
    fun resolve(errorView: T, throwable: Throwable, onRetry: OnRetry? = null, onResolved: OnResolved? = null) {
        when (throwable) {
            is ProtocolException, is UnknownServiceException -> {
                errorView.onUnauthorized(onRetry)
            }
            else -> errorView.onUndefinedError(throwable.message, onRetry, onResolved)
        }
        throwable.printStackTrace()
    }

    fun T.onUndefinedError(message: String?, onRetry: OnRetry?, onResolved: OnResolved?)

    fun T.onUnauthorized(onRetry: OnRetry? = null)
}
