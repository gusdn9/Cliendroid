package com.hyunwoo.cliendroid.presentation.fragment.search.board

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.search.board.BaseSearchItem
import com.hyunwoo.cliendroid.domain.model.search.board.Board
import com.hyunwoo.cliendroid.domain.model.search.board.SearchContent
import com.hyunwoo.cliendroid.domain.model.search.board.SearchSort
import com.hyunwoo.cliendroid.presentation.fragment.search.SearchArgs

data class State(
    val keyword: String,
    val sort: SearchSort = SearchSort.RECENCY,
    val boardId: String? = null,
    val page: Int = 0,
    val boardList: List<Board>? = null,
    val searchResultList: List<BaseSearchItem>? = null,
    val endOfPage: Boolean = false,
    val searchRefreshAsync: Async<SearchContent> = Uninitialized,
    val searchLoadMoreAsync: Async<SearchContent> = Uninitialized
) : MavericksState {
    constructor(args: SearchArgs) : this(
        keyword = args.keyword
    )
}
