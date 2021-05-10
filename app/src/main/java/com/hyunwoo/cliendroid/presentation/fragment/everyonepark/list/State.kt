package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.BaseEveryoneParkForum

data class State(
    val url: String,
    val listData: List<BaseEveryoneParkForum>? = null,
    val listDataRefreshAsync: Async<List<BaseEveryoneParkForum>> = Uninitialized,
    val listDataLoadMoreAsync: Async<List<BaseEveryoneParkForum>> = Uninitialized,
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
