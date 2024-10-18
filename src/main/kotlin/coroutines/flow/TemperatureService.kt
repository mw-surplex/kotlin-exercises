package coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import java.util.concurrent.ConcurrentHashMap

class TemperatureService(
    private val temperatureDataSource: TemperatureDataSource,
    backgroundScope: CoroutineScope,
) {
    private val lastKnownTemperature =
        ConcurrentHashMap<String, Fahrenheit>()

    fun getWeatherUpdates(city: String): Flow<Fahrenheit> =
        TODO()

    fun getLastKnownWeather(city: String): Fahrenheit? =
        lastKnownTemperature[city]

    fun gerAllLastKnownWeather(): Map<String, Fahrenheit> =
        lastKnownTemperature.toMap()

    private fun celsiusToFahrenheit(celsius: Double) =
        Fahrenheit(celsius * 9 / 5 + 32)
}

interface TemperatureDataSource {
    fun getWeatherStream(): Flow<TemperatureData>
}

data class TemperatureData(
    val city: String,
    val temperature: Double,
)

data class Fahrenheit(
    val temperature: Double,
)
