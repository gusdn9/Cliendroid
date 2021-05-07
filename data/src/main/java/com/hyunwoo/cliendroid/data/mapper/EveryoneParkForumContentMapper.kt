package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.EveryoneParkForumContent
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.everyonepark.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.everyonepark.CommentDto
import com.hyunwoo.cliendroid.network.model.everyonepark.EveryoneParkForumDetailRes

fun EveryoneParkForumDetailRes.toEveryoneParkForumContent(): EveryoneParkForumContent =
    EveryoneParkForumContent(
        title = title,
        user = User(user.id, user.nickName, user.image),
        time = time,
        hits = hits,
        likes = likes,
        ipAddress = ipAddress,
        htmlBody = htmlBody,
        comments.map { reply ->
            when (reply) {
                is CommentDto -> reply.toComment()
                is BlockedCommentDto -> reply.toComment()
            }
        }
    )
