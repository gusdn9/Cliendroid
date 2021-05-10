package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.BlockedForum
import com.hyunwoo.cliendroid.domain.model.Forum
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.forum.BlockedForumItemDto
import com.hyunwoo.cliendroid.network.model.forum.ForumItemDto

fun ForumItemDto.toForum() =
    Forum(
        id,
        title,
        link,
        replyCount,
        hit,
        time,
        likes,
        User(null, user.nickName, user.image)
    )

fun BlockedForumItemDto.toBlockedForum() =
    BlockedForum(
        id,
        title
    )
