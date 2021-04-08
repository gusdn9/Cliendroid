package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum

data class State(
    val listData: List<EveryoneParkForum>? = null,
    val getListAsync: Async<List<EveryoneParkForum>> = Uninitialized
) : MavericksState
