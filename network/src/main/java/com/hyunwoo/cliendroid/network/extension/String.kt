package com.hyunwoo.cliendroid.network.extension

import java.lang.NumberFormatException
import java.util.Locale

fun String.parseLargeNumber(): Long {
    val x = this.replace(" ", "").toLowerCase(Locale.US)
    val num = x.toLongOrNull()
    if(num != null) return num

    if(x.contains('k')) {
        return (x.replace("k", "").toBigDecimal()*1000.toBigDecimal()).toLong()
    }
    if(x.contains('m')) {
        return (x.replace("m", "").toBigDecimal()*1000000.toBigDecimal()).toLong()
    }
    if(x.contains('b')) {
        return (x.replace("b", "").toBigDecimal()*1000000000.toBigDecimal()).toLong()
    }
    if(x.contains('t')) {
        return (x.replace("b", "").toBigDecimal()*1000000000000.toBigDecimal()).toLong()
    }
    throw NumberFormatException()
}
