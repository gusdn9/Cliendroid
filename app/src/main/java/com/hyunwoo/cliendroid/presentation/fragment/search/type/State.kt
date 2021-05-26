package com.hyunwoo.cliendroid.presentation.fragment.search.type

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.search.type.BaseSearchTypeItem
import com.hyunwoo.cliendroid.domain.model.search.type.SearchType
import com.hyunwoo.cliendroid.domain.model.search.type.SearchTypeContent
import com.hyunwoo.cliendroid.presentation.fragment.search.SearchArgs

data class State(
    val keyword: String,
    val searchType: SearchType = SearchType.TITLE,
    val page: Int = 0,
    val searchResultList: List<BaseSearchTypeItem>? = null,
    val endOfPage: Boolean = false,
    val searchRefreshAsync: Async<SearchTypeContent> = Uninitialized,
    val searchLoadMoreAsync: Async<SearchTypeContent> = Uninitialized
) : MavericksState {
    constructor(args: SearchArgs) : this(
        keyword = args.keyword
    )
}
