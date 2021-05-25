package com.hyunwoo.cliendroid.domain.model.search.type

enum class SearchType(val value: String) {
    BOARD("search_board"),
    TITLE("search_title"),
    CONTENT("search_content"),
    COMMENT("search_comment"),
    WRITER("search_writer"),
    COMMENTER("search_commenter");

    companion object {
        fun getValueOf(type: String): SearchType? = SearchType.values().find { it.value == type }
    }
}
