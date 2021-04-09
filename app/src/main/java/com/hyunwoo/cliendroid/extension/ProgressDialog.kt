package com.hyunwoo.cliendroid.extension

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.hyunwoo.cliendroid.R

var FragmentManager.isProgressDialogVisible: Boolean
    get() = isShowing(this)
    set(value) {
        if (value) {
            show(this)
        } else {
            dismiss(this)
        }
    }

var Fragment.isProgressDialogVisible: Boolean
    get() = childFragmentManager.isProgressDialogVisible
    set(value) {
        childFragmentManager.isProgressDialogVisible = value
    }

var AppCompatActivity.isProgressDialogVisible: Boolean
    get() = supportFragmentManager.isProgressDialogVisible
    set(value) {
        supportFragmentManager.isProgressDialogVisible = value
    }

private const val TAG = "ProgressDialogFragment"

private fun getInstance(fragmentManager: FragmentManager): ProgressDialogFragment? =
    fragmentManager.findFragmentByTag(TAG) as? ProgressDialogFragment

private fun isShowing(fragmentManager: FragmentManager): Boolean =
    getInstance(fragmentManager) != null

private fun show(fragmentManager: FragmentManager) {
    if (getInstance(fragmentManager) != null) return
    val fragment = ProgressDialogFragment()
    fragment.show(fragmentManager, TAG)
}

private fun dismiss(fragmentManager: FragmentManager) {
    getInstance(fragmentManager)?.dismiss()
}

class ProgressDialogFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = false
        setStyle(STYLE_NORMAL, R.style.ThemeOverlay_Cliendroid_MaterialAlertDialog_Progress)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        return inflater.inflate(R.layout.dialog_progress, container, false)
    }
}
