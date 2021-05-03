package com.hyunwoo.cliendroid.extension

import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.ArrayAdapter
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showAlert(block: AlertBuilder.() -> Unit): AlertDialog =
    requireContext().showAlert(this, block)

fun Fragment.createAlert(block: AlertBuilder.() -> Unit): AlertDialog =
    requireContext().createAlert(this, block)

fun AppCompatActivity.showAlert(block: AlertBuilder.() -> Unit): AlertDialog =
    showAlert(this, block)

fun AppCompatActivity.createAlert(block: AlertBuilder.() -> Unit): AlertDialog =
    createAlert(this, block)

fun Context.showAlert(owner: LifecycleOwner, block: AlertBuilder.() -> Unit): AlertDialog =
    createAlert(owner, block).apply { show() }

fun Context.createAlert(owner: LifecycleOwner, block: AlertBuilder.() -> Unit): AlertDialog {
    val dialog = createAlert(this, block)

    owner.lifecycle.addObserver(object : LifecycleObserver {

        @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
        fun onDestroy() {
            if (dialog.isShowing) {
                dialog.dismiss()
            }
        }
    })

    return dialog
}

private fun createAlert(context: Context, block: AlertBuilder.() -> Unit): AlertDialog =
    AlertBuilder()
        .apply(block)
        .build(context) {
            val dialog = MaterialAlertDialogBuilder(context)
                .apply {
                    setTitle(title)
                    if (singleChoice != null) {
                        with(singleChoice) { setSingleChoiceItems(singleChoiceAdapter, checkedItem, listener) }
                    } else {
                        setMessage(message)

                        if (view != null) {
                            setView(view)
                        }
                    }
                    if (positiveButton != null) {
                        setPositiveButton(positiveButton.text) { _, _ ->
                            if (positiveButton.dismissOnClicked) {
                                positiveButton.onClicked()
                            }
                        }
                    }
                    if (negativeButton != null) {
                        setNegativeButton(negativeButton.text) { _, _ ->
                            if (negativeButton.dismissOnClicked) {
                                negativeButton.onClicked()
                            }
                        }
                    }
                    if (neutralButton != null) {
                        setNeutralButton(neutralButton.text) { _, _ ->
                            if (neutralButton.dismissOnClicked) {
                                neutralButton.onClicked()
                            }
                        }
                    }
                    if (onCanceled != null) {
                        setOnCancelListener { onCanceled.invoke() }
                    }
                    setCancelable(cancelable)
                }
                .create()
            dialog.setOnShowListener {
                if (positiveButton != null && positiveButton.dismissOnClicked.not()) {
                    dialog.getButton(DialogInterface.BUTTON_POSITIVE)?.setOnClickListener {
                        positiveButton.onClicked()
                    }
                }
                if (negativeButton != null && negativeButton.dismissOnClicked.not()) {
                    dialog.getButton(DialogInterface.BUTTON_NEGATIVE)?.setOnClickListener {
                        negativeButton.onClicked()
                    }
                }
                if (neutralButton != null && neutralButton.dismissOnClicked.not()) {
                    dialog.getButton(DialogInterface.BUTTON_NEUTRAL)?.setOnClickListener {
                        neutralButton.onClicked()
                    }
                }
            }
            dialog
        }

private fun <T> AlertBuilder.build(context: Context, block: AlertArgs.() -> T): T {
    val title = titleResId?.let(context::getString) ?: this.title
    val message = messageResId?.let(context::getString) ?: this.message
    val positiveButton = positiveButton?.let { button ->
        val text = button.textResId?.let(context::getString) ?: button.text
        button.copy(text = text)
    }
    val neutralButton = neutralButton?.let { button ->
        val text = button.textResId?.let(context::getString) ?: button.text
        button.copy(text = text)
    }
    val negativeButton = negativeButton?.let { button ->
        val text = button.textResId?.let(context::getString) ?: button.text
        button.copy(text = text)
    }
    return block(
        AlertArgs(
            title,
            message,
            view,
            singleChoice,
            positiveButton,
            negativeButton,
            neutralButton,
            onCanceled,
            cancelable
        )
    )
}

private data class AlertArgs(
    val title: String?,

    val message: String?,

    val view: View?,

    val singleChoice: SingleChoice?,

    val positiveButton: AlertButton?,

    val negativeButton: AlertButton?,

    val neutralButton: AlertButton?,

    val onCanceled: (() -> Unit)?,

    val cancelable: Boolean,
)

class AlertBuilder {
    var title: String? = null

    @StringRes
    var titleResId: Int? = null

    var message: String? = null

    @StringRes
    var messageResId: Int? = null

    var view: View? = null

    var singleChoice: SingleChoice? = null

    var positiveButton: AlertButton? = null

    var negativeButton: AlertButton? = null

    var neutralButton: AlertButton? = null

    var onCanceled: (() -> Unit)? = null

    var cancelable: Boolean = true
}

data class AlertButton(
    val text: String?,

    @StringRes
    val textResId: Int?,

    val dismissOnClicked: Boolean,

    val onClicked: () -> Unit
) {
    constructor(text: String?, dismissOnClicked: Boolean, onClicked: () -> Unit) :
            this(text, null, dismissOnClicked, onClicked)

    constructor(text: String?, onClicked: () -> Unit) :
            this(text, null, true, onClicked)

    constructor(text: String?) : this(text, null, true, {})

    constructor(textResId: Int, dismissOnClicked: Boolean, onClicked: () -> Unit) :
            this(null, textResId, dismissOnClicked, onClicked)

    constructor(textResId: Int, onClicked: () -> Unit) : this(null, textResId, true, onClicked)

    constructor(textResId: Int) : this(null, textResId, true, {})
}

data class SingleChoice(
    val singleChoiceAdapter: ArrayAdapter<CharSequence>,
    val checkedItem: Int,
    val listener: DialogInterface.OnClickListener
)
