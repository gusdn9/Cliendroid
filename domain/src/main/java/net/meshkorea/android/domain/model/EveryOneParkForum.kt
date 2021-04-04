package net.meshkorea.android.domain.model

data class EveryOneParkForum(
    val title: String,
    val link: String,
    val replyCount: Int?,
    val hit: Int?,
    val time: String,
    val likes: Int?,
    val user: User
)
