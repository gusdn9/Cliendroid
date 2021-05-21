package com.hyunwoo.cliendroid.presentation.fragment.search.type

import com.airbnb.mvrx.MavericksState
import com.hyunwoo.cliendroid.presentation.fragment.search.SearchArgs

data class State(
    val keyword: String
) : MavericksState {
    constructor(args: SearchArgs) : this(
        keyword = args.keyword
    )
}
