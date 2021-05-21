package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.forum.BlockedComment
import com.hyunwoo.cliendroid.domain.model.forum.Comment
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.forum.BlockedCommentDto
import com.hyunwoo.cliendroid.network.model.forum.CommentDto

fun CommentDto.toComment(): Comment =
    Comment(
        id = id,
        contents = contents,
        contentsImage = contentsImage,
        contentsVideo = contentsVideo,
        ipAddress = ipAddress,
        time = time,
        likes = likes,
        user = User(user.id, user.nickName, user.image),
        isReply = isReply
    )

fun BlockedCommentDto.toComment(): BlockedComment =
    BlockedComment(
        contents,
        isReply
    )
