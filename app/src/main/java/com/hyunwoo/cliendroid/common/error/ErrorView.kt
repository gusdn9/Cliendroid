package com.hyunwoo.cliendroid.common.error

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hyunwoo.cliendroid.extension.AlertBuilder
import com.hyunwoo.cliendroid.extension.showAlert

interface ErrorView {

    val context: Context

    val fragmentManager: FragmentManager

    fun showAlert(block: AlertBuilder.() -> Unit)

    fun finishApp()

    fun getString(@StringRes resId: Int, vararg formatArgs: Any?): String

    companion object {

        fun forActivity(activity: AppCompatActivity): ErrorView =
            ActivityBaseErrorView(activity)

        fun forFragment(fragment: Fragment): ErrorView =
            FragmentBaseErrorView(fragment)
    }
}

private class ActivityBaseErrorView(
    private val activity: AppCompatActivity
) : ErrorView {
    override val context: Context
        get() = activity

    override val fragmentManager: FragmentManager
        get() = activity.supportFragmentManager

    override fun showAlert(block: AlertBuilder.() -> Unit) {
        activity.showAlert(block)
    }

    override fun finishApp() {
        activity.finishAffinity()
    }

    override fun getString(resId: Int, vararg formatArgs: Any?): String =
        activity.getString(resId, *formatArgs)
}

private class FragmentBaseErrorView(
    private val fragment: Fragment
) : ErrorView {

    override val context: Context
        get() = fragment.requireContext()

    override val fragmentManager: FragmentManager
        get() = fragment.childFragmentManager

    override fun showAlert(block: AlertBuilder.() -> Unit) {
        fragment.showAlert(block)
    }

    override fun finishApp() {
        fragment.requireActivity().finishAffinity()
    }

    override fun getString(resId: Int, vararg formatArgs: Any?): String =
        fragment.getString(resId, *formatArgs)
}
