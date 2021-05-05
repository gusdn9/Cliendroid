package com.hyunwoo.cliendroid.common.error

import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.domain.model.LogoutCause
import com.hyunwoo.cliendroid.domain.usecase.auth.LogoutUseCase
import com.hyunwoo.cliendroid.extension.AlertButton

class FatalErrorResolvableImpl<T : ErrorView>(
    private val logoutUseCase: LogoutUseCase
) : FatalErrorResolvable<T> {

    override fun T.onUnauthorized(onRetry: OnRetry?) {
        showAlert {
            titleResId = R.string.error_view_alert_title
            messageResId = R.string.error_resolution_fatal_unauthorized
            positiveButton = AlertButton(R.string.error_view_alert_confirm) {
                logoutUseCase(LogoutCause.EXPIRED_SESSION)
            }
            cancelable = false
        }
    }
}
