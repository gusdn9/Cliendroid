package com.hyunwoo.cliendroid.network.model

data class SearchItemDto(
    val board: BoardDto,
    val title: String,
    val summary: String,
    val time: String,
    val hit: Long,
    val user: UserDto
)

data class BoardDto(
    val id: String,
    val name: String
)
