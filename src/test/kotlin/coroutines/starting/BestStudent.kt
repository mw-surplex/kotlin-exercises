package coroutines.starting

import kotlinx.coroutines.*
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.system.measureTimeMillis
import kotlin.test.assertEquals

class RequestTest {

    @Test
    fun `Function does return the best student in the semester`() = runTest {
        // given
        val semester = "19L"
        val best = Student(2, 95.0, semester)
        val repo = ImmediateFakeStudentRepo(
            listOf(
                Student(1, 90.0, semester),
                best,
                Student(3, 50.0, semester)
            )
        )
        val useCase = BestStudentUseCase(repo)

        // when
        val chosen = useCase.getBestStudent(semester)

        // then
        assertEquals(best, chosen)
    }

    @Test
    fun `When no students, correct error is thrown`() = runTest {
        // given
        val semester = "19L"
        val repo = ImmediateFakeStudentRepo(listOf())
        val useCase = BestStudentUseCase(repo)

        // when and then
        assertThrowsError<IllegalStateException> {
            useCase.getBestStudent(semester)
        }
    }

    @Test
    fun `Requests do not wait for each other`() = runTest {
        // given
        val repo = WaitingFakeStudentRepo()
        val useCase = BestStudentUseCase(repo)

        // when
        useCase.getBestStudent("AAA")

        // then
        assertEquals(1200, currentTime)
    }

    @Test
    fun `Cancellation works fine`() = runTest {
        // given
        val repo = WaitingFakeStudentRepo()
        val useCase = BestStudentUseCase(repo)

        // when
        val job = launch {
            useCase.getBestStudent("AAA")
        }
        delay(300)
        job.cancel()

        // then
        assertEquals(0, repo.returnedStudents)
    }

    @Test
    fun `When one request has error, all are stopped and error is thrown`() = runTest {
        // given
        val repo = FirstFailingFakeStudentRepo()
        val useCase = BestStudentUseCase(repo)

        // when and then
        assertThrowsError<FirstFailingFakeStudentRepo.FirstFailingError> {
            useCase.getBestStudent("AAA")
        }

        // then
        assertEquals(
            0,
            repo.studentsReturned,
            "Looks like some requests were still running after the first one had an error"
        )
    }
}

inline fun assertTimeAround(expectedTime: Int, upperMargin: Int = 100, body: () -> Unit) {
    val actualTime = measureTimeMillis(body)
    assert(actualTime in expectedTime..(expectedTime + upperMargin)) {
        "Operation should take around $expectedTime, but it took $actualTime"
    }
}

inline fun <reified T : Throwable> assertThrowsError(body: () -> Unit) {
    try {
        body()
        assert(false) { "There should be an error of type ${T::class.simpleName}" }
    } catch (throwable: Throwable) {
        if (throwable !is T) {
            throw throwable
        }
    }
}
