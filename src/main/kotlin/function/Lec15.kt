package com.example.function

/**
 * inline 함수는 본인만 인라이닌 되는게 아니라
 * 알 수 있는 함수 파라미터도 인라이닝 시키고
 * non-local return 역시 쓸 수 있게 해준다.
 *
 * inline 함수의 함수 파라미터를 인라이닝 시키고 싶지 않다면
 * noinline을 사용한다.
 *
 * inline 함수의 함수 파라미터가 non-local return을
 * 쓸 수 없게 하려면 crossinline을 사용한다.
 */

fun main() {
    repeat(2) { println("Hello") }

//    iterate(listOf(1, 2, 3, 4, 5)) { num ->
//        if (num == 3) {
//            return
//        }
//        println(num)
//    }
}

inline fun iterate(
    numbers: List<Int>,
    crossinline exac: (Int) -> Unit,
) {
    for (num in numbers) {
        exac(num)
    }
}

inline fun repeat(
    times: Int,
    noinline exac: () -> Unit,
) {
    for (i in 1..times) {
        exac
    }
}
