package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.EveryOneParkForum
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.EveryOneParkForumDto

fun EveryOneParkForumDto.toEveryOneParkForum() =
    EveryOneParkForum(
        title,
        link,
        replyCount,
        hit,
        time,
        likes,
        User(null, user.nickName, user.nickName)
    )
