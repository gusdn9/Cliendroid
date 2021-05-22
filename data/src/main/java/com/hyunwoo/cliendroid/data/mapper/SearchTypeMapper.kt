package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.domain.model.search.type.BlockedSearchTypeItem
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeContent
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeItem
import com.hyunwoo.cliendroid.network.model.search.auth.BlockedSearchAuthItemDto
import com.hyunwoo.cliendroid.network.model.search.auth.SearchAuthItemDto
import com.hyunwoo.cliendroid.network.model.search.auth.SearchAuthRes

fun SearchAuthRes.toSearchTypeContent(): SearchTypeContent =
    SearchTypeContent(
        contents = contents.map { item ->
            when (item) {
                is SearchAuthItemDto -> item.toSearchItem()
                is BlockedSearchAuthItemDto -> item.toSearchItem()
            }
        },
        endOfPage = endOfPage
    )

fun SearchAuthItemDto.toSearchItem(): SearchTypeItem =
    SearchTypeItem(
        id = id,
        title = title,
        replyCount = replyCount,
        likes = likes,
        board = board,
        link = link,
        time = time,
        hits = hits,
        user = User(user.id, user.nickName, user.image)
    )

fun BlockedSearchAuthItemDto.toSearchItem(): BlockedSearchTypeItem =
    BlockedSearchTypeItem(id = id, title = title)
