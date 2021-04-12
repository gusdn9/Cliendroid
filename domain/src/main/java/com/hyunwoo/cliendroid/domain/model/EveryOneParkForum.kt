package com.hyunwoo.cliendroid.domain.model

data class EveryoneParkForum(
    val id: Long,
    val title: String,
    val link: String,
    val replyCount: Int?,
    val hit: Int?,
    val time: String,
    val likes: Int?,
    val user: User
)
