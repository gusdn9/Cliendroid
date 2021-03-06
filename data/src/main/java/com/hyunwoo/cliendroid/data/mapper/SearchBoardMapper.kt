package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.search.board.BlockedSearchItem
import com.hyunwoo.cliendroid.domain.model.search.board.Board
import com.hyunwoo.cliendroid.domain.model.search.board.SearchContent
import com.hyunwoo.cliendroid.domain.model.search.board.SearchItem
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.search.withoutauth.BlockedSearchItemDto
import com.hyunwoo.cliendroid.network.model.search.withoutauth.SearchItemDto
import com.hyunwoo.cliendroid.network.model.search.withoutauth.SearchRes

fun SearchRes.toSearchContent(): SearchContent =
    SearchContent(
        searchList = contents.map { item ->
            when (item) {
                is SearchItemDto -> item.toSearchItem()
                is BlockedSearchItemDto -> item.toSearchItem()
            }
        },
        boardList = boards.map { Board(it.id, it.name) },
        endOfPage = endOfPage
    )

fun SearchItemDto.toSearchItem(): SearchItem =
    SearchItem(
        id = id,
        board = Board(board.id, board.name),
        title = title,
        summary = summary,
        link = link,
        time = time,
        hit = hit,
        user = User(user.id, user.nickName, user.image)
    )

fun BlockedSearchItemDto.toSearchItem(): BlockedSearchItem =
    BlockedSearchItem(id, title)
