package functional.collections.passingStudents

fun List<Student>.makePassingStudentsList(): String = TODO()

data class Student(
    val name: String,
    val surname: String,
    val result: Double,
    val pointsInSemester: Int
)
