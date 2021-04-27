package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.Board
import com.hyunwoo.cliendroid.domain.model.SearchContent
import com.hyunwoo.cliendroid.domain.model.SearchItem
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.SearchItemDto
import com.hyunwoo.cliendroid.network.model.SearchRes

fun SearchRes.toSearchContent(): SearchContent =
    SearchContent(
        searchList = contents.map { it.toSearchItem() },
        boardList = boards.map { Board(it.id, it.name) }
    )

fun SearchItemDto.toSearchItem(): SearchItem =
    SearchItem(
        board = Board(board.id, board.name),
        title = title,
        summary = summary,
        link = link,
        time = time,
        hit = hit,
        user = User(user.id, user.nickName, user.image)
    )
