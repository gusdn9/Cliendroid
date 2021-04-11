package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.EveryoneParkForumContent
import com.hyunwoo.cliendroid.network.model.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.CommentDto
import com.hyunwoo.cliendroid.network.model.EveryoneParkForumDetailRes

fun EveryoneParkForumDetailRes.toEveryoneParkForumContent(): EveryoneParkForumContent =
    EveryoneParkForumContent(
        htmlBody,
        comments.map { reply ->
            when (reply) {
                is CommentDto -> reply.toComment()
                is BlockedCommentDto -> reply.toComment()
            }
        }
    )
