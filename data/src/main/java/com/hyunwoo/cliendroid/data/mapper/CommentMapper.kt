package com.hyunwoo.cliendroid.data.mapper

import com.hyunwoo.cliendroid.domain.model.Comment
import com.hyunwoo.cliendroid.domain.model.User
import com.hyunwoo.cliendroid.network.model.CommentDto

fun CommentDto.toComment(): Comment =
    Comment(
        id,
        title,
        User(user.id, user.nickName, user.image)
    )
