package com.hyunwoo.cliendroid.network.model

sealed class BaseSearchItemDto(
    open val id: Long,
    open val title: String
)

data class BlockedSearchItemDto(
    override val id: Long,
    override val title: String
) : BaseSearchItemDto(id, title)

data class SearchItemDto(
    override val id: Long,
    override val title: String,
    val board: BoardItemDto,
    val summary: String,
    val link: String,
    val time: String,
    val hit: Long,
    val user: UserDto
) : BaseSearchItemDto(id, title)

data class BoardItemDto(
    val id: String,
    val name: String
)
