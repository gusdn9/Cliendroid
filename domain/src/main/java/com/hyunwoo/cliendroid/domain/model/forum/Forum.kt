package com.hyunwoo.cliendroid.domain.model.forum

import com.hyunwoo.cliendroid.domain.model.User

sealed class BaseForum(
    open val id: Long,
    open val title: String
)

data class Forum(
    override val id: Long,
    override val title: String,
    val link: String,
    val replyCount: Long?,
    val hit: Long?,
    val time: String,
    val likes: Long?,
    val user: User
) : BaseForum(id, title)

data class BlockedForum(
    override val id: Long,
    override val title: String,
) : BaseForum(id, title)
