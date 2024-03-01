package com.example.function

fun main() {
    compute(1, 2) { a, b -> a + b }

    compute(
        1,
        2,
        fun(
            a,
            b,
        ) = a + b,
    )
}

fun compute(
    num1: Int,
    num2: Int,
    op: (Int, Int) -> Int,
): Int {
    return op(num1, num2)
}

fun calculate(
    num1: Int,
    num2: Int,
    oper: Operator,
) = oper(num1, num2)

enum class Operator(
    private val oper: Char,
    val calcFun: (Int, Int) -> Int,
) {
    PLUS('+', { a, b -> a + b }),
    MINUS('-', { a, b -> a - b }),
    MULTIPLY('*', { a, b -> a * b }),
    DIVIDE('/', { a, b ->
        if (b == 0) {
            throw IllegalArgumentException()
        } else {
            a / b
        }
    }),
    ;

    operator fun invoke(
        a: Int,
        b: Int,
    ): Int = this.calcFun(a, b)
}
