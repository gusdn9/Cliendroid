package com.hyunwoo.cliendroid.extension

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.bundleOf
import com.airbnb.mvrx.Mavericks

private const val ACTIVITY_ARG_KEY = "activity:args"
private const val FRAGMENT_ARG_KEY = Mavericks.KEY_ARG

fun Parcelable?.toActivityArgsBundle(): Bundle = bundleOf(ACTIVITY_ARG_KEY to this)
fun Parcelable?.toFragmentArgsBundle(): Bundle = bundleOf(FRAGMENT_ARG_KEY to this)
