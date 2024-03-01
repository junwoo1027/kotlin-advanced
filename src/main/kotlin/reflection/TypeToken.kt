package com.example.reflection

import com.example.generic.Animal
import com.example.generic.Carp
import com.example.generic.GoldFish
import kotlin.reflect.KClass
import kotlin.reflect.KType
import kotlin.reflect.cast

fun main() {
//    val typeSafeCage = TypeSafeCage()
//
//    typeSafeCage.putOne(Carp("carp"))
//    typeSafeCage.getOne<Carp>()

    val spType1 = object : SuperTypeToken<List<GoldFish>>() {}
    val spType2 = object : SuperTypeToken<List<GoldFish>>() {}
    val spType3 = object : SuperTypeToken<List<Carp>>() {}

    val superTypeSafeCage = SuperTypeSafeCage()
    superTypeSafeCage.putOne(spType1, listOf(GoldFish("fish1"), GoldFish("fish2")))
    val result = superTypeSafeCage.getOne(spType1)
    println(result)
}

class TypeSafeCage() {
    private val animals: MutableMap<KClass<*>, Animal> = mutableMapOf()

    fun <T: Animal> getOne(type: KClass<T>): T {
        return type.cast(animals[type])
    }

    fun <T: Animal> putOne(type: KClass<T>, animal: Animal) {
        animals[type] = type.cast(animal)
    }

    inline fun <reified T: Animal> getOne(): T {
        return this.getOne(T::class)
    }

    inline fun <reified T: Animal> putOne(animal: T) {
        this.putOne(T::class, animal)
    }
}

abstract class SuperTypeToken<T> {
    val type: KType = this::class.supertypes[0].arguments[0].type!!

    override fun equals(other: Any?): Boolean {
        if (this == other) return true

        other as SuperTypeToken<*>

        return type == other.type
    }

    override fun hashCode(): Int {
        return type.hashCode()
    }
}

class SuperTypeSafeCage {
    private val animals: MutableMap<SuperTypeToken<*>, Any> = mutableMapOf()

    fun <T: Any> getOne(token: SuperTypeToken<T>): T {
        if(this.animals[token] == null) {
            throw IllegalArgumentException()
        }
        return this.animals[token] as T
    }

    fun <T: Any> putOne(token: SuperTypeToken<T>, animal: T) {
        animals[token] = animal
    }
}