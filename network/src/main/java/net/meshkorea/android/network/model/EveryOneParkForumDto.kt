package net.meshkorea.android.network.model

import java.util.Date

data class EveryOneParkForumDto(
    val title: String,
    val link: String,
    val replyCount: Int?,
    val hit: Int,
    val time: Date,
    val like: Int,
    val userDto: UserDto
)
