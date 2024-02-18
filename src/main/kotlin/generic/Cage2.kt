package com.example.generic

fun main() {
    val fishCage = Cage2<Fish>()
    val animalCage: Cage2<Animal> = fishCage
}
class Cage2<out T> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return animals.first()
    }

    fun getAll(): List<T> {
        return this.animals
    }
}