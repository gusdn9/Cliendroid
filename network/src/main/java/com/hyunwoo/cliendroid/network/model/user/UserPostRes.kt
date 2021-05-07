package com.hyunwoo.cliendroid.network.model.user

data class UserPostRes(
    val posts: List<UserPostItemDto>,
    val endOfPage: Boolean
)
