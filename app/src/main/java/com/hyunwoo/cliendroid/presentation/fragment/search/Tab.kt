package com.hyunwoo.cliendroid.presentation.fragment.search

enum class Tab(val value: Int) {
    BOARD_SEARCH(0),
    TYPE_SEARCH(1);

    companion object {
        val ALL = values()

        fun fromValue(value: Int): Tab {
            return ALL[value]
        }
    }
}
