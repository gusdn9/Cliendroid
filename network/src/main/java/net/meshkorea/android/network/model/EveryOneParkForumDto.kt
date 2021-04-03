package net.meshkorea.android.network.model

import java.util.Date

data class EveryOneParkForumDto(
    val title: String,
    val link: String,
    val replyCount: Int?,
    val hit: Int?,
    val time: String,
    val likes: Int?,
    val user: User
) {
    data class User(
        val nickName: String?,
        val image: String?
    )
}
