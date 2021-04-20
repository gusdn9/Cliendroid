package com.hyunwoo.cliendroid.data.entity

data class LoggedInUserEntity(
    val userId: String,
    val userNickname: String,
    val userEmail: String?,
    val startDate: String?
)
