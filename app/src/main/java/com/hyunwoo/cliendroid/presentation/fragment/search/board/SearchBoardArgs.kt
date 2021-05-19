package com.hyunwoo.cliendroid.presentation.fragment.search.board

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchBoardArgs(
    val keyword: String
) : Parcelable
