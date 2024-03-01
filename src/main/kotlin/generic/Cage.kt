package com.example.generic

fun main() {
    val goldFishCage = Cage<GoldFish>()
    goldFishCage.put(GoldFish("금붕어"))

    val cage = Cage<Fish>()
    cage.moveFrom(goldFishCage)

    goldFishCage.moveTo(cage)
}

// Generic Class
class Cage<T : Any> {
    private val animals: MutableList<T> = mutableListOf()

    fun getFirst(): T {
        return animals.first()
    }

    fun put(animal: T) {
        this.animals.add(animal)
    }

    fun moveFrom(otherCage: Cage<out T>) { // 생산자, 공변: 타입 파라미터의 상속 관계가 제네릭 클래스에서 유지된다.
        this.animals.addAll(otherCage.animals)
    }

    fun moveTo(otherCage: Cage<in T>) { // 소비자, 반공변: 타입 파라미터의 상속관계가 제네릭 클래스에서 반대로 된다.
        otherCage.animals.addAll(this.animals)
    }
}
