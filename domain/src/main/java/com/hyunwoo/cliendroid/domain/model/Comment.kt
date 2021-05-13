package com.hyunwoo.cliendroid.domain.model

sealed class BaseComment(open val isReply: Boolean = false)

data class Comment(
    val id: Long,
    val contents: String,
    val contentsImage: String? = null,
    val contentsVideo: String? = null,
    val ipAddress: String,
    val time: String,
    val likes: Long,
    val user: User,
    override val isReply: Boolean
) : BaseComment(isReply)

data class BlockedComment(
    val contents: String,
    override val isReply: Boolean
) : BaseComment(isReply)
