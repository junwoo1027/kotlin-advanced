package com.example.generic

interface Fruit {
    val name: String
    val color: String
    fun bite()
}

open class Apple: Fruit {
    override val name: String
        get() = "사과"
    override val color: String
        get() = "빨간색"

    override fun bite() {
        println("아삭")
    }
}

class GreenApple: Fruit {
    override val name: String
        get() = "사과"
    override val color: String
        get() = "초록색"

    override fun bite() {
        println("아삭")
    }
}

class GreenApple2: Apple() {
    override val color: String
        get() = "초록색"
}

class GreenApple3(
    private val apple: Apple
): Fruit {
    override val name: String
        get() = apple.name
    override val color: String
        get() = "초록색"

    override fun bite() {
        apple.bite()
    }
}

class GreenApple4(
    private val apple: Apple
): Fruit by apple {
    override val name: String
        get() = "초록색"
}