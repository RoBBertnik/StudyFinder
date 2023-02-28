package com.example.studyfinder.model
import java.io.Serializable

sealed class Course (val name: String) : Serializable{
    object Mathe_1 : Course("Mathe 1")
    object Mathe_2 : Course("Mathe 2")
    object Ap_1: Course("Algorithmen und Programmieren 1")
    object Ap_2: Course("Algorithmen und Programmieren 2")
    object MoCo: Course("Mobile Computing")
}

val courseList = listOf(
    Course.Mathe_1,
    Course.Mathe_2,
    Course.Ap_1,
    Course.Ap_2,
    Course.MoCo
)

