package com.hyunwoo.cliendroid.network.model.search.withoutauth

data class SearchRes(
    val contents: List<BaseSearchItemDto>,
    val boards: List<BoardItemDto>,
    val endOfPage: Boolean
)
