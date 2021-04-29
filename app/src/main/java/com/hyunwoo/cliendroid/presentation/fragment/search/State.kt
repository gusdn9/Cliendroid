package com.hyunwoo.cliendroid.presentation.fragment.search

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.BaseSearchItem
import com.hyunwoo.cliendroid.domain.model.Board
import com.hyunwoo.cliendroid.domain.model.SearchContent
import com.hyunwoo.cliendroid.domain.model.SearchItem
import com.hyunwoo.cliendroid.domain.model.SearchSort

data class State(
    val keyword: String,
    val sort: SearchSort = SearchSort.RECENCY,
    val boardId: String? = null,
    val page: Int = 0,
    val boardList: List<Board>? = null,
    val searchResultList: List<BaseSearchItem>? = null,
    val searchRefreshAsync: Async<SearchContent> = Uninitialized,
    val searchLoadMoreAsync: Async<SearchContent> = Uninitialized
) : MavericksState {
    constructor(args: SearchArgs) : this(
        keyword = args.keyword
    )
}
