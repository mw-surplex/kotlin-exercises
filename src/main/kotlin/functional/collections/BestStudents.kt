package functional.collections

import functional.collections.passingStudents.Student

fun List<Student>.makeBestStudentsList(): String = TODO()

data class Student(
    val name: String,
    val surname: String,
    val result: Double,
    val pointsInSemester: Int
)
