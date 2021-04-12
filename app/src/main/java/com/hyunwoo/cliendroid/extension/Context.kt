package com.hyunwoo.cliendroid.extension

import android.content.Context
import android.util.TypedValue
import androidx.core.content.ContextCompat

fun Context.getColorWithAttr(attr: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(attr, typedValue, true)
    if (typedValue.resourceId == 0) {
        return typedValue.data
    }
    return ContextCompat.getColor(this, typedValue.resourceId)
}
