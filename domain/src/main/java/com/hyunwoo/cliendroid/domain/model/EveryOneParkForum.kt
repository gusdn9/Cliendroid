package com.hyunwoo.cliendroid.domain.model

sealed class BaseEveryoneParkForum(
    open val id: Long,
    open val title: String
)

data class EveryoneParkForum(
    override val id: Long,
    override val title: String,
    val link: String,
    val replyCount: Long?,
    val hit: Long?,
    val time: String,
    val likes: Long?,
    val user: User
) : BaseEveryoneParkForum(id, title)

data class BlockedEveryoneParkForum(
    override val id: Long,
    override val title: String,
) : BaseEveryoneParkForum(id, title)
