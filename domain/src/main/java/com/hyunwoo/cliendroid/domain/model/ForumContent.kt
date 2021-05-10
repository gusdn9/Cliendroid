package com.hyunwoo.cliendroid.domain.model

data class ForumContent(
    val title: String,
    val user: User,
    val time: String,
    val hits: String,
    val likes: Long,
    val ipAddress: String,
    val htmlBody: String,
    val comments: List<BaseComment>
)
