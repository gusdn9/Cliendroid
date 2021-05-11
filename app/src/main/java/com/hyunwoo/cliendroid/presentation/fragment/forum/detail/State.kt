package com.hyunwoo.cliendroid.presentation.fragment.forum.detail

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.ForumContent
import com.hyunwoo.cliendroid.domain.model.LoggedInUser

data class State(
    val url: String,
    val content: ForumContent? = null,
    val loggedInUser: LoggedInUser? = null,
    val refreshAsync: Async<ForumContent> = Uninitialized
) : MavericksState {
    constructor(args: ForumDetailArgs) : this(
        url = args.forumLink
    )
}
