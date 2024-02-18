package com.example.generic

fun main() {
    val cage = Cage4(mutableListOf(Eagle(), Sparrow()))
    cage.printAfterSorting()
}

abstract  class Bird(
    name: String,
    private val size: Int
): Animal(name), Comparable<Bird> {
    override fun compareTo(other: Bird): Int {
        return this.size.compareTo(other.size)
    }
}

class Sparrow: Bird("참새", 100)
class Eagle: Bird("독수리", 200)

class Cage4<T>(
    private val animals: MutableList<T> = mutableListOf()
) where T : Animal, T : Comparable<T> {

    fun printAfterSorting() {
        this.animals.sorted()
            .map { it.name }
            .let(::println)
    }

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage4<out T>) { // 생산자, 공변: 타입 파라미터의 상속 관계가 제네릭 클래스에서 유지된다.
        this.animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage4<in T>) { // 소비자, 반공변: 타입 파라미터의 상속관계가 제네릭 클래스에서 반대로 된다.
        otherCage.animals.addAll(this.animals)
    }
}

fun <T> List<T>.hasIntersection(other: List<T>): Boolean {
    return (this.toSet() intersect other.toSet()).isNotEmpty()
}