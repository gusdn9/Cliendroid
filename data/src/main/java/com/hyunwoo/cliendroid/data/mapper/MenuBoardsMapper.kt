package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.MenuBoardItem
import com.hyunwoo.cliendroid.domain.model.MenuBoards
import com.hyunwoo.cliendroid.network.model.BoardRes
import com.hyunwoo.cliendroid.network.model.MenuBoardDto

fun BoardRes.toMenuBoards(): MenuBoards =
    MenuBoards(
        communities = communities.map { it.toMenuBoardItem() },
        somoimList = somoimList.map { it.toMenuBoardItem() }
    )

fun MenuBoardDto.toMenuBoardItem(): MenuBoardItem =
    MenuBoardItem(name = name, link = link)
