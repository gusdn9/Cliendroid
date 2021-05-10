package com.hyunwoo.cliendroid.network.model.forum

import com.hyunwoo.cliendroid.network.model.user.UserDto

sealed class BaseListItemDto(
    open val id: Long,
    open val title: String
)

data class ForumItemDto(
    override val id: Long,
    override val title: String,
    val link: String,
    val replyCount: Long?,
    val hit: Long?,
    val time: String,
    val likes: Long?,
    val user: UserDto
) : BaseListItemDto(id, title)

data class BlockedForumItemDto(
    override val id: Long,
    override val title: String
) : BaseListItemDto(id, title)
