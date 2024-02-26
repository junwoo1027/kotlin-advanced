package com.example.dsl

import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

fun StringBuilder.appendNew(str: String, indent: String = "", times: Int = 0) {
    (1..times).forEach { _ -> this.append(indent) }
    this.append(str)
    this.append("\n")
}

/**
 * abcd
 * efg
 */
fun String.addIndent(indent: String, times: Int = 0): String {
    val allIndent = (1..times).joinToString("") { indent }
    return this.split("\n")
        .joinToString("\n") { "$allIndent$it" }
}

fun <T> onceNotNull() = object : ReadWriteProperty<Any?, T> {
    private var value: T? = null
    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        if (this.value == null) {
            throw IllegalArgumentException("변수가 초기화되지 않았습니다")
        }
        return this.value!!
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        if (this.value != null) {
            throw IllegalArgumentException("이 변수는 한 번만 값을 초기화할 수 있습니다")
        }
        this.value = value
    }
}

operator fun String.minus(other: String): Environment {
    return Environment(
        key = this,
        value = other
    )
}