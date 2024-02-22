package com.example.generic

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