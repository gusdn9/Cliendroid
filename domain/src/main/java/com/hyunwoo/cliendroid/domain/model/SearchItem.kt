package com.hyunwoo.cliendroid.domain.model

data class SearchItem(
    val board: Board,
    val title: String,
    val summary: String,
    val link: String,
    val time: String,
    val hit: Long,
    val user: User
)
