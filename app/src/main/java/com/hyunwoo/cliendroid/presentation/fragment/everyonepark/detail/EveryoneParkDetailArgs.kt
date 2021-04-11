package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class EveryoneParkDetailArgs(
    val forumLink: String
) : Parcelable
