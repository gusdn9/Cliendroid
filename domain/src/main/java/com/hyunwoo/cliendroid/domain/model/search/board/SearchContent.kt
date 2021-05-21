package com.hyunwoo.cliendroid.domain.model.search.board

data class SearchContent(
    val searchList: List<BaseSearchItem>,
    val boardList: List<Board>,
    val endOfPage: Boolean
)
