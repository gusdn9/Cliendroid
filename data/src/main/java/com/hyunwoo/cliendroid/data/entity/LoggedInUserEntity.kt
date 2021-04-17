package com.hyunwoo.cliendroid.data.entity

data class LoggedInUserEntity(
    val cookies: Set<String>,
    val userId: String,
    val userNickname: String
)
