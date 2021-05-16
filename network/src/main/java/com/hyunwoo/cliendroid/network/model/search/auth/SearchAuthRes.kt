package com.hyunwoo.cliendroid.network.model.search.auth

data class SearchAuthRes(
    val contents: List<SearchAuthItemDto>,
    val endOfPage: Boolean
)
