package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.UserInfo
import com.hyunwoo.cliendroid.network.model.UserInfoRes

fun UserInfoRes.toUserInfo() =
    UserInfo(nickname, startDate, email)
