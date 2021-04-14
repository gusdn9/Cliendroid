package com.hyunwoo.cliendroid.network.model

sealed class BaseEveryoneParkForumItemDto(
    open val id: Long,
    open val title: String
)

data class EveryoneParkForumItemDto(
    override val id: Long,
    override val title: String,
    val link: String,
    val replyCount: Long?,
    val hit: Long?,
    val time: String,
    val likes: Long?,
    val user: UserDto
) : BaseEveryoneParkForumItemDto(id, title)

data class BlockedEveryoneParkForumItemDto(
    override val id: Long,
    override val title: String
) : BaseEveryoneParkForumItemDto(id, title)
