package com.example.reflection

@Repeatable
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.FILE, AnnotationTarget.CLASS)
annotation class Shape(
    val texts: Array<String>,
)

@Shape(["A", "C"])
@Shape(["A", "B"])
class Annotation
