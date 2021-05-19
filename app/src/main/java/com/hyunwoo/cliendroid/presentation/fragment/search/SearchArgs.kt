package com.hyunwoo.cliendroid.presentation.fragment.search

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SearchArgs(
    val keyword: String
) : Parcelable
