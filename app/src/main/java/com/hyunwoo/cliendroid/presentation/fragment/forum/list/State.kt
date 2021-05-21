package com.hyunwoo.cliendroid.presentation.fragment.forum.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.forum.BaseForum

data class State(
    val url: String,
    val listData: List<BaseForum>? = null,
    val listDataRefreshAsync: Async<List<BaseForum>> = Uninitialized,
    val listDataLoadMoreAsync: Async<List<BaseForum>> = Uninitialized,
    val page: Int = 0
) : MavericksState {
    constructor() : this(
        url = DEFAULT_LINK
    )

    constructor(args: ForumListArgs) : this(
        url = args.link
    )

    companion object {
        // 모두의 공원
        private const val DEFAULT_TITLE = "모두의 공원"
        private const val DEFAULT_LINK = "service/board/park"
    }
}
