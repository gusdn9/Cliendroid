package com.hyunwoo.cliendroid.presentation.fragment.everyonepark.detail

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.BaseComment
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForumContent

data class State(
    val url: String,
    val content: EveryoneParkForumContent? = null,
    val refreshAsync: Async<EveryoneParkForumContent> = Uninitialized
) : MavericksState {
    constructor(args: EveryoneParkDetailArgs) : this(
        url = args.forumLink
    )
}
