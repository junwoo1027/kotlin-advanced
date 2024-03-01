package com.example.function

import function.StringFilter

fun add(
    a: Int,
    b: Int,
): Int {
    return a + b
}

fun main() {
    // SAM 생성자
    val filter = StringFilter { str -> str?.startsWith("A") ?: false }

    val kStringFilter = KStringFilter { it.startsWith("*") }

    val add2 = ::add

    println(add2(1, 2))
}

fun interface KStringFilter {
    fun predicate(str: String): Boolean
}
