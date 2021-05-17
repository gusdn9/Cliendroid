package com.hyunwoo.cliendroid.network.model.search.auth

data class SearchAuthRes(
    val contents: List<BaseSearchAuthItemDto>,
    val endOfPage: Boolean
)
