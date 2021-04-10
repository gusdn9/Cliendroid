package com.hyunwoo.cliendroid.network.model

data class CommentDto(
    val id: Long,
    val title: String,
    val ipAddress: String,
    val time: String,
    val user: UserDto
)
