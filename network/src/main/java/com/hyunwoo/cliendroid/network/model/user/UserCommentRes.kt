package com.hyunwoo.cliendroid.network.model.user

data class UserCommentRes(
    val userComments: List<UserCommentItemDto>,
    val endOfPage: Boolean
)
