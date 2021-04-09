package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum

data class State(
    val listData: List<EveryoneParkForum>? = null,
    val listDataRefreshAsync: Async<List<EveryoneParkForum>> = Uninitialized,
    val listDataLoadMoreAsync: Async<List<EveryoneParkForum>> = Uninitialized,
    val page: Int = 0
) : MavericksState
