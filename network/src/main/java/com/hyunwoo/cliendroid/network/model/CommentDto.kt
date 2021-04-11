package com.hyunwoo.cliendroid.network.model

sealed class BaseCommentDto(open val isReply: Boolean = false)

data class CommentDto(
    val id: Long,
    val contents: String,
    val ipAddress: String,
    val time: String,
    val user: UserDto,
    override val isReply: Boolean
) : BaseCommentDto(isReply)

data class BlockedCommentDto(
    val contents: String,
    override val isReply: Boolean
) : BaseCommentDto(isReply)
