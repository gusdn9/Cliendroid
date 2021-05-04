package com.hyunwoo.cliendroid.common.error.snackbar

import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hyunwoo.cliendroid.R
import com.hyunwoo.cliendroid.common.SnackbarHolder
import com.hyunwoo.cliendroid.common.error.FatalErrorResolvable
import com.hyunwoo.cliendroid.common.error.FatalErrorResolvableImpl
import com.hyunwoo.cliendroid.common.error.OnResolved
import com.hyunwoo.cliendroid.common.error.OnRetry
import com.hyunwoo.cliendroid.common.error.Resolution
import com.hyunwoo.cliendroid.domain.usecase.auth.LogoutUseCase
import javax.inject.Inject

class SnackbarErrorResolution @Inject constructor(
    logoutUseCase: LogoutUseCase
) : Resolution<SnackbarErrorView>,
    FatalErrorResolvable<SnackbarErrorView> by FatalErrorResolvableImpl(logoutUseCase) {

    /**
     * 에러를 resolution의 로직에 맞춰 resolve한다.
     *
     * @param activity 에러를 resolve할때 사용할 activity
     * @param snackbarHolder 에러를 resolve할때 사용할 snackbarHolder
     * @param throwable resolve할 에러
     * @param onRetry non-null일 경우 재시도할 수 있는 방법을 제공하여 해당 callback이 실행되게 한다.
     */
    fun resolve(
        activity: AppCompatActivity,
        snackbarHolder: SnackbarHolder,
        throwable: Throwable,
        onRetry: OnRetry? = null
    ) {
        resolve(SnackbarErrorView.forActivity(activity, snackbarHolder), throwable, onRetry)
    }

    /**
     * 에러를 resolution의 로직에 맞춰 resolve한다.
     *
     * @param fragment 에러를 resolve할때 사용할 fragment
     * @param snackbarHolder 에러를 resolve할때 사용할 snackbarHolder
     * @param throwable resolve할 에러
     * @param onRetry non-null일 경우 재시도할 수 있는 방법을 제공하여 해당 callback이 실행되게 한다.
     */
    fun resolve(
        fragment: Fragment,
        snackbarHolder: SnackbarHolder,
        throwable: Throwable,
        onRetry: OnRetry? = null
    ) {
        resolve(SnackbarErrorView.forFragment(fragment, snackbarHolder), throwable, onRetry)
    }

    override fun SnackbarErrorView.onUndefinedError(message: String?, onRetry: OnRetry?, onResolved: OnResolved?) {
        showSnackbar(onRetry, R.string.error_resolution_undefined_error, message, onRetry)
    }

    private fun SnackbarErrorView.showSnackbar(
        onRetry: OnRetry?,
        @StringRes resId: Int,
        vararg formatArgs: Any?
    ) {
        showSnackbar(onRetry, getString(resId, *formatArgs))
    }

    private fun SnackbarErrorView.showSnackbar(onRetry: OnRetry?, message: String) {
        if (onRetry != null) {
            snackbarHolder.showRetry(message, onRetry)
        } else {
            snackbarHolder.showConfirm(message)
        }
    }
}
