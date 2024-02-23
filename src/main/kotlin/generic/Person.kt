package com.example.generic

import kotlin.properties.Delegates

class Person {
    lateinit var name: String // lateinit: 초기화를 지연시킨 변수, 초기화 로직이 여러 곳에 위치할 수 있다. 초기화 없이 호출하면 예외 발생
    val isKim: Boolean
        get() = this.name.startsWith("김")

    val maskingName: String
        get() = name[0] + (1 until name.length).joinToString("") { "*" }
}

class Person2 {
    val name: String by lazy { // lazy: 초기화를 get 호출 전으로 지연시킨 변수. 초기화 로직은 변수 선언과 동시에 한 곳에만 위치 할 수 있다.
        Thread.sleep(2000)
        "김준우"
    }
}

class Person3 {
    var age: Int by Delegates.observable(20) { _, oldValue, newValue ->
        println("이전 값: $oldValue 새로운 값: $newValue")
    }
}

class Person4 {
    var age: Int by Delegates.vetoable(20) { _, _, newValue -> newValue >= 1}
}

fun main() {
    val p = Person3()
    p.age = 30

    val p2 = Person4()
    p2.age = 0
    println(p2.age)
}