package com.hyunwoo.cliendroid.domain.repository

import com.hyunwoo.cliendroid.domain.model.LoggedInUser

interface LoggedInUserDataSource {

    var loggedInUser: LoggedInUser?
}
