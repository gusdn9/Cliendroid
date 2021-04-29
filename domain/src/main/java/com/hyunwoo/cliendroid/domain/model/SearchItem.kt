package com.hyunwoo.cliendroid.domain.model

sealed class BaseSearchItem(
    open val id: Long,
    open val title: String
)

data class SearchItem(
    override val id: Long,
    override val title: String,
    val board: Board,
    val summary: String,
    val link: String,
    val time: String,
    val hit: Long,
    val user: User
) : BaseSearchItem(id, title)

data class BlockedSearchItem(
    override val id: Long,
    override val title: String
) : BaseSearchItem(id, title)
