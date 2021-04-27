package com.hyunwoo.cliendroid.network.model

data class SearchRes(
    val contents: List<SearchItemDto>,
    val boards: List<BoardItemDto>
)
