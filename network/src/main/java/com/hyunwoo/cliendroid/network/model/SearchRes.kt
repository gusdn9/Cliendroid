package com.hyunwoo.cliendroid.network.model

data class SearchRes(
    val contents: List<BaseSearchItemDto>,
    val boards: List<BoardItemDto>
)
