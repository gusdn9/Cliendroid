package com.hyunwoo.cliendroid.domain.model.search.type

data class SearchTypeContent(
    val contents: List<BaseSearchTypeItem>,
    val endOfPage: Boolean
)
