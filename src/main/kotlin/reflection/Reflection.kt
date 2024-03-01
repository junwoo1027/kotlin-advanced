package com.example.reflection

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.full.createType
import kotlin.reflect.full.hasAnnotation

@Target(AnnotationTarget.CLASS)
annotation class Executable

@Executable
class Reflection {
    fun a() {
        println("A")
    }

    fun b(n: Int) {
        println("B")
    }
}

fun executeAll(obj: Any) {
    val kClass: KClass<out Any> = obj::class

    if (!kClass.hasAnnotation<Executable>()) {
        return
    }

    val callableFunctions =
        kClass.members.filterIsInstance<KFunction<*>>()
            .filter { it.returnType == Unit::class.createType() }
            .filter { it.parameters.size == 1 && it.parameters[0].type == kClass.createType() }

    callableFunctions.forEach { function ->
        function.call(obj)
    }
}

fun main() {
    executeAll(Reflection())
}
