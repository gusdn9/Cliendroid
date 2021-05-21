package com.hyunwoo.cliendroid.domain.model.search.type

import com.hyunwoo.cliendroid.domain.model.User

sealed class BaseSearchTypeItem(
    open val id: Long,
    open val title: String
)

data class BlockedSearchTypeItem(
    override val id: Long,
    override val title: String
) : BaseSearchTypeItem(id, title)

data class SearchTypeItem(
    override val id: Long,
    override val title: String,
    val replyCount: Long,
    val likes: Long,
    val board: String,
    val link: String,
    val time: String,
    val hits: Long,
    val user: User
) : BaseSearchTypeItem(id, title)
