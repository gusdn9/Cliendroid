package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.data.entity.LoggedInUserEntity
import com.hyunwoo.cliendroid.domain.model.LoggedInUser

fun LoggedInUserEntity.toLoggedInUser() =
    LoggedInUser(
        cookies,
        userId,
        userNickname
    )

fun LoggedInUser.toUserEntity() =
    LoggedInUserEntity(
        cookies,
        userId,
        userNickname
    )
