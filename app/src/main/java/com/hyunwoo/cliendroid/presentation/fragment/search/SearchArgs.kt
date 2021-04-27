package com.hyunwoo.cliendroid.presentation.fragment.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchArgs(
    val keyword: String
) : Parcelable
