package coroutines.flow

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.currentTime
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class TemperatureServiceTest {

    @Test
    fun `should emit temperature updates in Fahrenheit`() = runTest {
        // given
        val testDataSource = object : TemperatureDataSource {
            override fun getWeatherStream(): Flow<TemperatureData> = flow {
                emit(TemperatureData("TestCity", 10.0))
                emit(TemperatureData("TestCity2", 20.0))
                emit(TemperatureData("TestCity", 30.0))
                emit(TemperatureData("TestCity3", 40.0))
                emit(TemperatureData("TestCity2", 50.0))
            }
        }
        val service = TemperatureService(testDataSource, backgroundScope)

        // when
        val emitted = service.getWeatherUpdates("TestCity").toList()

        // then
        assertEquals(listOf(Fahrenheit(50.0), Fahrenheit(86.0)), emitted)
    }

    @Test
    fun `should store last known temperature update in Fahrenheit`() = runTest {
        // given
        val testDataSource = object : TemperatureDataSource {
            override fun getWeatherStream(): Flow<TemperatureData> = flow {
                delay(100)
                emit(TemperatureData("TestCity", 10.0))
                delay(100)
                emit(TemperatureData("TestCity2", 20.0))
                delay(100)
                emit(TemperatureData("TestCity", 30.0))
                delay(100)
                emit(TemperatureData("TestCity3", 40.0))
                delay(100)
                emit(TemperatureData("TestCity2", 50.0))
            }
        }
        val service = TemperatureService(testDataSource, backgroundScope)

        // when
        val emitted = mutableListOf<Fahrenheit>()
        service.getWeatherUpdates("TestCity")
            .onEach { emitted.add(it) }
            .launchIn(backgroundScope)

        delay(150)
        assertEquals(Fahrenheit(50.0), service.getLastKnownWeather("TestCity"))
        assertEquals(Fahrenheit(50.0), service.gerAllLastKnownWeather()["TestCity"])

        delay(200)
        assertEquals(Fahrenheit(86.0), service.getLastKnownWeather("TestCity"))
        assertEquals(Fahrenheit(86.0), service.gerAllLastKnownWeather()["TestCity"])
    }

    @Test
    fun `should emit last known temperature update on start`() = runTest {
        // given
        val testDataSource = object : TemperatureDataSource {
            override fun getWeatherStream(): Flow<TemperatureData> = flow {
                delay(100)
                emit(TemperatureData("TestCity", 10.0))
                delay(100)
                emit(TemperatureData("TestCity2", 20.0))
            }
        }
        val service = TemperatureService(testDataSource, backgroundScope)
        service.getWeatherUpdates("TestCity").first()
        assertEquals(100, currentTime)

        // when
        val result = service.getWeatherUpdates("TestCity").first()

        // then
        assertEquals(Fahrenheit(50.0), result)
        assertEquals(100, currentTime)

        // when
        val result2 = service.getWeatherUpdates("TestCity2").first()

        // then
        assertEquals(Fahrenheit(68.0), result2)
    }
}
