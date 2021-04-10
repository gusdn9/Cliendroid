package com.hyunwoo.cliendroid.network.model

data class EveryoneParkForumListDto(
    val title: String,
    val link: String,
    val replyCount: Int?,
    val hit: Int?,
    val time: String,
    val likes: Int?,
    val user: UserDto
)
