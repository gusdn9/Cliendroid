package com.hyunwoo.cliendroid.presentation.fragment.forum.list

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumListArgs(
    val title: String,
    val link: String
) : Parcelable
