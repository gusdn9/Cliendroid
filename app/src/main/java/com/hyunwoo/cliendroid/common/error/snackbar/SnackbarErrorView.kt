package com.hyunwoo.cliendroid.common.error.snackbar

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hyunwoo.cliendroid.common.SnackbarHolder
import com.hyunwoo.cliendroid.common.error.ErrorView

interface SnackbarErrorView : ErrorView {

    val snackbarHolder: SnackbarHolder

    companion object {

        fun forActivity(
            activity: AppCompatActivity,
            snackbarHolder: SnackbarHolder
        ): SnackbarErrorView =
            ActivitySnackbarErrorView(activity, snackbarHolder)

        fun forFragment(
            fragment: Fragment,
            snackbarHolder: SnackbarHolder
        ): SnackbarErrorView =
            FragmentSnackbarErrorView(fragment, snackbarHolder)
    }
}

private class ActivitySnackbarErrorView(
    private val activity: AppCompatActivity,
    override val snackbarHolder: SnackbarHolder
) : SnackbarErrorView,
    ErrorView by ErrorView.forActivity(activity)

private class FragmentSnackbarErrorView(
    private val fragment: Fragment,
    override val snackbarHolder: SnackbarHolder
) : SnackbarErrorView,
    ErrorView by ErrorView.forFragment(fragment)
