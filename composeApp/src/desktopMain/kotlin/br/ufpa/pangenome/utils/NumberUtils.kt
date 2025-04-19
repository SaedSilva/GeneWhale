package br.ufpa.pangenome.utils

import kotlin.math.roundToInt

fun Long.toMB(): Long {
    return this / (1024 * 1024)
}

fun Float.toMB(mb: Long): Long {
    return (this * mb).toLong()
}

fun Float.toThreads(maxThreads: Int): Int {
    return (this * maxThreads).roundToInt()
}

fun String.isValidFloat(): Boolean {
    return try {
        this.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}