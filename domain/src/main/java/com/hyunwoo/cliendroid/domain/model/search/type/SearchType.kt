package com.hyunwoo.cliendroid.domain.model.search.type

enum class SearchType(val value: String) {
    TITLE("title"),
    CONTENT("content"),
    COMMENT("comment"),
    WRITER("id"),
    COMMENTER("commenter");

    companion object {
        fun getValueOf(type: String): SearchType? = SearchType.values().find { it.value == type }
    }
}
