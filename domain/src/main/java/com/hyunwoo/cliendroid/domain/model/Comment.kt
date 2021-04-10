package com.hyunwoo.cliendroid.domain.model

data class Comment(
    val id: Long,
    val title: String,
    val user: User
)
