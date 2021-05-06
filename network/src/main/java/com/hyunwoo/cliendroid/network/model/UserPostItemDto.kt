package com.hyunwoo.cliendroid.network.model

data class UserPostItemDto(
    val likes: String,
    val title: String,
    val boardName: String,
    val replyCount: String,
    val time: String,
    val link: String
)
