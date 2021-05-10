package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.BlockedComment
import com.hyunwoo.cliendroid.domain.model.Comment
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.forum.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.forum.CommentDto

fun CommentDto.toComment(): Comment =
    Comment(
        id,
        contents,
        ipAddress,
        time,
        likes,
        User(user.id, user.nickName, user.image),
        isReply
    )

fun BlockedCommentDto.toComment(): BlockedComment =
    BlockedComment(
        contents,
        isReply
    )
