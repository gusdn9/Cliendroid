package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.BaseEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum

data class State(
    val listData: List<BaseEveryoneParkForum>? = null,
    val listDataRefreshAsync: Async<List<BaseEveryoneParkForum>> = Uninitialized,
    val listDataLoadMoreAsync: Async<List<BaseEveryoneParkForum>> = Uninitialized,
    val page: Int = 0
) : MavericksState
