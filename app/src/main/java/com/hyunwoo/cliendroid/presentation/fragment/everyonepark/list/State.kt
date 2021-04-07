package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.list

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.EveryOneParkForum

data class State(
    val listData: List<EveryOneParkForum>? = null,
    val getListAsync: Async<List<EveryOneParkForum>> = Uninitialized
) : MavericksState
