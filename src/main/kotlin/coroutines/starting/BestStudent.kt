package coroutines.starting

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

class BestStudentUseCase(
    private val repo: StudentsRepository
) {
    suspend fun getBestStudent(semester: String): Student = TODO()
}

data class Student(val id: Int, val result: Double, val semester: String)

interface StudentsRepository {
    suspend fun getStudentIds(semester: String): List<Int>
    suspend fun getStudent(id: Int): Student
}


class ImmediateFakeStudentRepo(
    private val students: List<Student>
) : StudentsRepository {

    override suspend fun getStudentIds(semester: String): List<Int> =
        students.filter { it.semester == semester }
            .map { it.id }

    override suspend fun getStudent(id: Int): Student =
        students.first { it.id == id }
}

class WaitingFakeStudentRepo : StudentsRepository {
    var returnedStudents = 0

    override suspend fun getStudentIds(semester: String): List<Int> {
        delay(200)
        return (1..5).toList()
    }

    override suspend fun getStudent(id: Int): Student {
        delay(1000)
        returnedStudents++
        return Student(12, 12.0, "AAA")
    }
}

class FirstFailingFakeStudentRepo : StudentsRepository {
    var first = true
    var studentsReturned = 0
    val mutex = Mutex()

    override suspend fun getStudentIds(semester: String): List<Int> {
        delay(200)
        return (1..5).toList()
    }

    override suspend fun getStudent(id: Int): Student {
        delay(100)
        mutex.withLock {
            if (first) {
                first = false
                throw FirstFailingError()
            }
        }
        delay(100)
        studentsReturned++
        return Student(12, 12.0, "AAA")
    }

    class FirstFailingError() : Error()
}
