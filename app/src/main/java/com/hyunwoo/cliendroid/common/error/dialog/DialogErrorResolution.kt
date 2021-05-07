package com.hyunwoo.cliendroid.common.error.dialog

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.common.error.OnResolved
import com.hyunwoo.cliendroid.common.error.OnRetry
import com.hyunwoo.cliendroid.common.error.Resolution
import com.hyunwoo.cliendroid.domain.model.LogoutCause
import com.hyunwoo.cliendroid.domain.usecase.auth.LogoutUseCase
import com.hyunwoo.cliendroid.extension.AlertButton
import javax.inject.Inject

class DialogErrorResolution @Inject constructor(
    private val logoutUseCase: LogoutUseCase
) : Resolution<DialogErrorView> {

    /**
     * 에러를 resolution의 로직에 맞춰 resolve한다.
     *
     * @param activity 에러를 resolve할때 사용할 activity
     * @param throwable resolve할 에러
     * @param onRetry non-null일 경우 재시도할 수 있는 방법을 제공하여 해당 callback이 실행되게 한다.
     */
    fun resolve(
        activity: AppCompatActivity,
        throwable: Throwable,
        onRetry: OnRetry? = null,
        onResolved: OnResolved? = null
    ) {
        resolve(DialogErrorView.forActivity(activity), throwable, onRetry, onResolved)
    }

    /**
     * 에러를 resolution의 로직에 맞춰 resolve한다.
     *
     * @param fragment 에러를 resolve할때 사용할 fragment
     * @param throwable resolve할 에러
     * @param onRetry non-null일 경우 재시도할 수 있는 방법을 제공하여 해당 callback이 실행되게 한다.
     */
    fun resolve(
        fragment: Fragment,
        throwable: Throwable,
        onRetry: OnRetry? = null,
        onResolved: OnResolved? = null
    ) {
        resolve(DialogErrorView.forFragment(fragment), throwable, onRetry, onResolved)
    }

    override fun DialogErrorView.onUndefinedError(message: String?, onRetry: OnRetry?, onResolved: OnResolved?) {
        showAlert(onRetry, onResolved, R.string.error_resolution_undefined_error, message)
    }

    @Suppress("unused")
    private fun DialogErrorView.showAlert(
        onRetry: OnRetry?,
        onResolved: OnResolved?,
        @StringRes resId: Int,
        vararg formatArgs: Any?
    ) {
        showAlert(onRetry, onResolved, getString(resId, *formatArgs))
    }

    @Suppress("unused")
    private fun DialogErrorView.showAlert(onRetry: OnRetry?, onResolved: OnResolved?, message: String) {
        showAlert {
            this.cancelable = false
            this.titleResId = R.string.error_view_alert_title
            this.message = message
            this.positiveButton = AlertButton(R.string.error_view_alert_confirm) {
                if (onResolved != null) {
                    onResolved()
                }
            }
            if (onRetry != null) {
                this.neutralButton = AlertButton(R.string.error_view_alert_retry) {
                    onRetry()
                }
            }
        }
    }

    override fun DialogErrorView.onUnauthorized(onRetry: OnRetry?) {
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
