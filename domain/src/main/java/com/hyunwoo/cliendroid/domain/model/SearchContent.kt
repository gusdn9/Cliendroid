package com.hyunwoo.cliendroid.domain.model

data class SearchContent(
    val searchList: List<BaseSearchItem>,
    val boardList: List<Board>,
    val endOfPage: Boolean
)
