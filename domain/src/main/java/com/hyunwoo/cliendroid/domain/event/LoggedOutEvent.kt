package com.hyunwoo.cliendroid.domain.event

import com.hyunwoo.cliendroid.domain.model.LogoutCause

data class LoggedOutEvent(val cause: LogoutCause)
