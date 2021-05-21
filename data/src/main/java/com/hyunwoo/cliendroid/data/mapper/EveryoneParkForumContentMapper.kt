package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.forum.ForumContent
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.forum.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.forum.CommentDto
import com.hyunwoo.cliendroid.network.model.forum.ForumDetailRes

fun ForumDetailRes.toForumContent(): ForumContent =
    ForumContent(
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
