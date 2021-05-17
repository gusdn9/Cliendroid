package com.hyunwoo.cliendroid.network.model.search.auth

import com.hyunwoo.cliendroid.network.model.user.UserDto

sealed class BaseSearchAuthItemDto(
    open val id: Long,
    open val title: String
)

data class BlockedSearchAuthItemDto(
    override val id: Long,
    override val title: String
) : BaseSearchAuthItemDto(id, title)

data class SearchAuthItemDto(
    override val id: Long,
    override val title: String,
    val replyCount: Long,
    val likes: Long,
    val board: String,
    val link: String,
    val time: String,
    val hits: Long,
    val user: UserDto
) : BaseSearchAuthItemDto(id, title)
