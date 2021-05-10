package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ForumDetailArgs(
    val forumLink: String
) : Parcelable
