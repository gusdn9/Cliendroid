package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.BlockedEveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.EveryoneParkForum
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.BlockedEveryoneParkForumItemDto
import com.hyunwoo.cliendroid.network.model.EveryoneParkForumItemDto

fun EveryoneParkForumItemDto.toEveryoneParkForum() =
    EveryoneParkForum(
        id,
        title,
        link,
        replyCount,
        hit,
        time,
        likes,
        User(null, user.nickName, user.image)
    )

fun BlockedEveryoneParkForumItemDto.toBlockedEveryoneParkForum() =
    BlockedEveryoneParkForum(
        id,
        title
    )
