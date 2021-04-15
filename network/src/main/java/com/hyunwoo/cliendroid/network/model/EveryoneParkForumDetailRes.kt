package com.hyunwoo.cliendroid.network.model

data class EveryoneParkForumDetailRes(
    val title: String,
    val user: UserDto,
    val time: String,
    val hits: String,
    val likes: Long,
    val ipAddress: String,
    val htmlBody: String,
    val comments: List<BaseCommentDto>
)
