package com.hyunwoo.cliendroid.presentation.fragment.search

import com.airbnb.mvrx.MavericksState
import com.hyunwoo.cliendroid.domain.model.LoggedInUser

data class State(
    val loggedInUser: LoggedInUser? = null
) : MavericksState
