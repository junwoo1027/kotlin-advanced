package com.example.extra

import kotlin.system.measureTimeMillis

class Lec24 {
}

// TODO 1
fun main() {
//    TODO("2")
    repeat(3) {
        println("hello")
    }

    val time = measureTimeMillis {
        val a = 1
        val b = 2
//        Thread.sleep(2_000)
        val result = a + b
    }

//    acceptOnlyTwo(1)

    val result = runCatching { 1 / 0 }
}

fun acceptOnlyTwo(num: Int) {
    require(num == 2) { "2만 허용" } // IllegalArgumentException
}

class Person {
    private val status =  PersonStatus.PLAYING

    fun sleep() {
        check(this.status == PersonStatus.PLAYING) { "error!" } // IllegalStateException
    }

    enum class PersonStatus {
        PLAYING,
        SLEEPING
    }
}