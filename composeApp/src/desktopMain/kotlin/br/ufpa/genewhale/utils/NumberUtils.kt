package br.ufpa.genewhale.utils

import kotlin.math.roundToInt

/**
 * Converts a given size in bytes to megabytes.
 */
fun Long.toMB(): Long {
    return this / (1024 * 1024)
}

/**
 * Converts a part of a given size in megabytes to bytes.
 * @param maxMemory The max size in megabytes.
 * This is used to calculate the size in bytes based on a percentage.
 */
fun Float.toMB(maxMemory: Long): Long {
    return (this * maxMemory).toLong()
}

/**
 * Converts a part of a given thread count to an integer thread count.
 * @param maxThreads The max thread count.
 * This is used to calculate the number of threads based on a percentage.
 */
fun Float.toThreads(maxThreads: Int): Int {
    return (this * maxThreads).roundToInt()
}

/**
 * Checks if a string is a valid float.
 */
fun String.isValidFloat(): Boolean {
    return try {
        this.toDouble()
        true
    } catch (e: NumberFormatException) {
        false
    }
}

/**
 * Checks if a string is a valid integer.
 */
fun String.isValidInt(): Boolean {
    return try {
        this.toInt()
        true
    } catch (e: NumberFormatException) {
        false
    }
}