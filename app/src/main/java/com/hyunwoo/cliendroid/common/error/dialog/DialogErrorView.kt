package com.hyunwoo.cliendroid.common.error.dialog

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.hyunwoo.cliendroid.common.error.ErrorView

interface DialogErrorView : ErrorView {

    companion object {

        fun forActivity(activity: AppCompatActivity): DialogErrorView =
            ActivityDialogErrorView(activity)

        fun forFragment(fragment: Fragment): DialogErrorView =
            FragmentDialogErrorView(fragment)
    }
}

private class ActivityDialogErrorView(activity: AppCompatActivity) : DialogErrorView,
    ErrorView by ErrorView.forActivity(activity)

private class FragmentDialogErrorView(fragment: Fragment) : DialogErrorView,
    ErrorView by ErrorView.forFragment(fragment)
