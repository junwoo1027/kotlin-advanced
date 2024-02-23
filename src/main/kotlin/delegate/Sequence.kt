package com.example.delegate

fun main() {
    val fruits = listOf(
        MyFruit("apple", 1000L),
        MyFruit("banana", 3000L)
    )

    val avg = fruits.asSequence()
        .filter { it.name == "apple" }
        .map { it.price }
        .take(10000)
        .average()

    println(avg)
}

data class MyFruit(
    val name: String,
    val price: Long
)