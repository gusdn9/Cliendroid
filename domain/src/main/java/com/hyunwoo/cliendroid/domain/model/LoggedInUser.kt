package com.hyunwoo.cliendroid.domain.model

data class LoggedInUser(
    val cookies: Set<String>,
    val userId: String,
    val userNickname: String,
    val userEmail: String?,
    val startDate: String?
)
