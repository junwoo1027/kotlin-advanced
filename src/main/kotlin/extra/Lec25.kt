package com.example.extra

import kotlin.reflect.KClass

fun main() {
    val userId = Id<User>(1)
    val bookId = Id<Book>(2)

    handle(userId, bookId)

    try {
        logic(10)
    } catch (e: Exception) {
        when (e) {
            is AException,
            is BException,
            -> println("A, B Exception")
            is CException -> println("C Exception")
        }
    }

    runCatching { logic(10) }
        .onError(AException::class, BException::class) {
            println("A, B Exception")
        }
}

fun logic(i: Int) {
    throw CException()
}

fun handle(
    userId: Id<User>,
    bookId: Id<Book>,
) {
}

class User(
    val id: Id<User>,
    val name: String,
)

class Book(
    val id: Id<Book>,
    val author: String,
)

@JvmInline
value class Id<T>(val id: Long)

fun <T> Result<T>.onError(
    vararg exceptions: KClass<out Throwable>,
    action: (Throwable) -> Unit,
): ResultWrapper<T> {
    exceptionOrNull()?.let {
        if (it::class in exceptions) {
            action(it)
        }
    }
    return ResultWrapper(this, exceptions.toMutableList())
}

class ResultWrapper<T>(
    private val result: Result<T>,
    private val knownExceptions: MutableList<KClass<out Throwable>>,
) {
    fun toResult(): Result<T> {
        return this.result
    }

    fun onError(
        vararg exceptions: KClass<out Throwable>,
        action: (Throwable) -> Unit,
    ): ResultWrapper<T> {
        this.result.exceptionOrNull()?.let {
            if (it::class in exceptions && it::class !in this.knownExceptions) {
                action(it)
            }
        }
        return this
    }
}

class AException : RuntimeException()

class BException : RuntimeException()

class CException : RuntimeException()
