package com.hyunwoo.cliendroid.domain.event

import com.hyunwoo.cliendroid.domain.model.LoggedInUser

data class LoggedInEvent(val user: LoggedInUser)
