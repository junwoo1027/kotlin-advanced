package com.example.reflection

import org.reflections.Reflections
import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KParameter
import kotlin.reflect.cast

class DI

object ContainerV1 {
    private val registeredClasses = mutableSetOf<KClass<*>>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

    fun <T: Any> getInstance(type: KClass<T>): T {
        return registeredClasses.firstOrNull { clazz -> clazz == type }
            ?.let { clazz -> clazz.constructors.first().call() as T }
            ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다")
    }
}

fun start(clazz: KClass<*>) {
    val reflections = Reflections(clazz.packageName)
    val jClasses = reflections.getTypesAnnotatedWith(MyClass::class.java)
    jClasses.forEach { jClass -> ContainerV2.register(jClass.kotlin) }
}

private val KClass<*>.packageName: String
    get() {
        val qualifiedName = this.qualifiedName
            ?: throw IllegalArgumentException("익명 객체입니다")
        val hierarchy =  qualifiedName.split(".")
        return hierarchy.subList(0, hierarchy.lastIndex).joinToString(".")
    }

object ContainerV2 {
    private val registeredClasses = mutableSetOf<KClass<*>>()
    private val cachedInstances = mutableMapOf<KClass<*>, Any>()

    fun register(clazz: KClass<*>) {
        registeredClasses.add(clazz)
    }

     fun <T: Any> getInstance(type: KClass<T>): T {
        if (type in cachedInstances) {
            return type.cast(cachedInstances[type])
        }

        val instance = registeredClasses.firstOrNull { clazz -> clazz == type }
            ?.let { clazz -> instantiate(clazz) as T }
            ?: throw IllegalArgumentException("해당 인스턴스 타입을 찾을 수 없습니다")

        cachedInstances[type] = instance
        return instance
    }

    private fun <T: Any> instantiate(clazz: KClass<T>):T {
        val constructor = findUsableConstructor(clazz)
        val params = constructor.parameters
            .map { param -> getInstance(param.type.classifier as KClass<*>) }
            .toTypedArray()
        return constructor.call(*params)
    }

    // clazz의 constructor들 중, 사용할 수 있는 constructor
    // constructor에 넣어햐 하는 타입들이 모두 등록된 경우 (컨테이너에서 관리하고 있는 경우)
    private fun <T: Any> findUsableConstructor(clazz: KClass<T>): KFunction<T> {
        return clazz.constructors
            .firstOrNull { constructor -> constructor.parameters.isAllRegistered }
            ?: throw IllegalArgumentException("사용할 수 있는 생성자가 없습니다")
    }

    private val List<KParameter>.isAllRegistered: Boolean
        get() = this.all { it.type.classifier in registeredClasses }
}

fun main() {
//    ContainerV1.register(ServiceA::class)
//    val serviceA = ContainerV1.getInstance(ServiceA::class)
//    serviceA.print()
//
//    ContainerV2.register(ServiceA::class)
//    ContainerV2.register(ServiceB::class)
//
//    val serviceB = ContainerV2.getInstance(ServiceB::class)
//    serviceB.print()


    start(DI::class)
    val serviceB = ContainerV2.getInstance(ServiceB::class)
    serviceB.print()
}

annotation class MyClass

@MyClass
class ServiceA {
    fun print() {
        println("A")
    }
}

@MyClass
class ServiceB(
    private val serviceA: ServiceA,
    private val serviceC: ServiceC?
) {

    constructor(serviceA: ServiceA): this(serviceA, null)

    fun print() {
        serviceA.print()
    }
}

class ServiceC