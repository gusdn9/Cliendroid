package com.hyunwoo.cliendroid.presentation.fragment.drawer

import com.airbnb.mvrx.Async
import com.airbnb.mvrx.MavericksState
import com.airbnb.mvrx.Uninitialized
import com.hyunwoo.cliendroid.domain.model.LoggedInUser
import com.hyunwoo.cliendroid.domain.model.MenuBoards

data class State(
    val loggedInUser: LoggedInUser? = null,
    val menuList: MenuBoards? = null,
    val loginAsync: Async<LoggedInUser> = Uninitialized,
    val menuListAsync: Async<MenuBoards> = Uninitialized
) : MavericksState
