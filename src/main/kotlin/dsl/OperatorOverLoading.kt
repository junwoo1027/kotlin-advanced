package com.example.dsl

data class Point(
    val x: Int,
    val y: Int
) {
    operator fun unaryMinus(): Point {
        return Point(-x, -y)
    }

    operator fun inc(): Point {
        return Point(x + 1, y + 1)
    }
}

fun main() {
    var point = Point(20, -10)
    println(-point)
    println(++point)

    val list = mutableListOf("A", "B", "C")
    list += "D"

    var list2 = listOf("A", "B", "C")
    list2 += "D"

    val map = mutableMapOf(1 to "A")
    map[2] = "B"

    for ((key, value) in map.entries) {
        println(key)
        println(value)
    }
}