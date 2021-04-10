package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.EveryoneParkForumListDto

fun EveryoneParkForumListDto.toEveryoneParkForum() =
    EveryoneParkForum(
        title,
        link,
        replyCount,
        hit,
        time,
        likes,
        User(null, user.nickName, user.image)
    )
