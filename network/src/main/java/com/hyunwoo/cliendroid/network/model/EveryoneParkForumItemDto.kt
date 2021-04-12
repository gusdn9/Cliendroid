package com.hyunwoo.cliendroid.network.model

data class EveryoneParkForumItemDto(
    val id: Long,
    val title: String,
    val link: String,
    val replyCount: Int?,
    val hit: Int?,
    val time: String,
    val likes: Int?,
    val user: UserDto
)
