package com.hyunwoo.cliendroid.common

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.snackbar.Snackbar
import com.hyunwoo.cliendroid.R

open class SnackbarHolder(protected val getAnchorView: () -> View) : LifecycleObserver {

    private val snackbarList = mutableListOf<Snackbar>()

    private val snackbarCallback = object : Snackbar.Callback() {

        override fun onDismissed(snackbar: Snackbar, event: Int) {
            snackbarList -= snackbar
            snackbarList.lastOrNull()?.show()
        }
    }

    private fun addQueue(snackbar: Snackbar) {
        snackbar.addCallback(snackbarCallback)
        val first = snackbarList.isEmpty()
        snackbarList += snackbar
        if (first) {
            snackbar.show()
        }
    }

    protected open fun createSnackbar(message: String, duration: Int): Snackbar =
        getAnchorView().createSnackbar(message, duration)

    fun showSnackbar(
        message: String,
        duration: Int = Snackbar.LENGTH_INDEFINITE,
        block: Snackbar.() -> Unit = {}
    ) {
        addQueue(createSnackbar(message, duration).apply(block))
    }

    fun showSnackbar(
        @StringRes messageResId: Int,
        duration: Int = Snackbar.LENGTH_INDEFINITE,
        block: Snackbar.() -> Unit = {}
    ) = showSnackbar(getAnchorView().context.getString(messageResId), duration, block)

    fun showRetry(@StringRes messageResId: Int, retry: () -> Unit) {
        showSnackbar(messageResId) {
            setAction(R.string.snackbar_retry) {
                retry()
            }
        }
    }

    fun showRetry(message: String, retry: () -> Unit) {
        showSnackbar(message) {
            setAction(R.string.snackbar_retry) {
                retry()
            }
        }
    }

    fun showConfirm(@StringRes messageResId: Int, onConfirmed: () -> Unit = {}) {
        showSnackbar(messageResId) {
            setAction(R.string.snackbar_confirm) { onConfirmed() }
        }
    }

    fun showConfirm(message: String, onConfirmed: () -> Unit = {}) {
        showSnackbar(message) {
            setAction(R.string.snackbar_confirm) { onConfirmed() }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        snackbarList.lastOrNull()?.dismiss()
        snackbarList.clear()
    }

    fun View.createSnackbar(
        message: String,
        duration: Int = Snackbar.LENGTH_INDEFINITE
    ): Snackbar =
        Snackbar.make(this, message, duration)

    class Factory(private val anchorId: Int) {

        fun create(fragment: Fragment): SnackbarHolder =
            forFragment(fragment, anchorId)

        fun create(activity: AppCompatActivity): SnackbarHolder =
            forActivity(activity, anchorId)
    }

    companion object {

        fun forFragment(fragment: Fragment, anchorId: Int): SnackbarHolder =
            SnackbarHolder {
                fragment.view?.findViewById(anchorId)
                    ?: fragment.requireActivity().findViewById(anchorId)
            }.apply {
                fragment.lifecycle.addObserver(this)
            }

        fun forActivity(activity: AppCompatActivity, anchorId: Int): SnackbarHolder =
            SnackbarHolder { activity.findViewById(anchorId) }.apply {
                activity.lifecycle.addObserver(this)
            }
    }
}
