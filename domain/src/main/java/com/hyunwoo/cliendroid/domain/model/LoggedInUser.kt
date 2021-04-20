package com.hyunwoo.cliendroid.domain.model

data class LoggedInUser(
    val userId: String,
    val userNickname: String,
    val userEmail: String?,
    val startDate: String?
)
