package com.example.generic

fun main() {
    val numbers = listOf(1, 2f, 3.0)
    numbers.filterIsInstance<Float>() // [2f]
}

inline fun <reified T> List<*>.hasAnyInstanceOf() : Boolean {
    return this.any { it is T }
}
class TypeErase {
}