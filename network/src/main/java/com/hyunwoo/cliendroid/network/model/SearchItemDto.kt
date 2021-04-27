package com.hyunwoo.cliendroid.network.model

data class SearchItemDto(
    val board: BoardItemDto,
    val title: String,
    val summary: String,
    val link: String,
    val time: String,
    val hit: Long,
    val user: UserDto
)

data class BoardItemDto(
    val id: String,
    val name: String
)
