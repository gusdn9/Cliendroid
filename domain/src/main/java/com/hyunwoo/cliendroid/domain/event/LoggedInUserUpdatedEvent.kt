package com.hyunwoo.cliendroid.domain.event

import com.hyunwoo.cliendroid.domain.model.LoggedInUser

data class LoggedInUserUpdatedEvent(val prev: LoggedInUser, val curr: LoggedInUser)
