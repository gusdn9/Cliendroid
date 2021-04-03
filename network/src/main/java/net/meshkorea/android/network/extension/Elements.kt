package net.meshkorea.android.network.extension

import org.jsoup.select.Elements

fun Elements.textOrNull() =
    if (this.hasText()) {
        this.text()
    } else {
        null
    }
