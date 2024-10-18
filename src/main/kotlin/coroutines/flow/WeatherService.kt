package coroutines.flow

import kotlinx.coroutines.flow.*

class WeatherService(
    private val weatherDataSource: WeatherDataSource
) {
    var lastWeatherUpdate: WeatherUpdate? = null
        private set

    fun getWeatherUpdates(city: String): Flow<WeatherUpdate> =
        weatherDataSource.getWeatherStream(city)
            .filter { it.temperature > 0 } 
            .map { dataToUpdate(city, it) }
            .onEach { lastWeatherUpdate = it }
            .onCompletion { lastWeatherUpdate = null }
            .onStart { emit(WeatherUpdate.Loading) }

    fun celsiusToFahrenheit(celsius: Double): Double = 
        celsius * 9 / 5 + 32

    private fun dataToUpdate(
        city: String, 
        weatherData: WeatherData
    ): WeatherUpdate = WeatherUpdate.Success(
      city = city, 
      temperature = celsiusToFahrenheit(weatherData.temperature)
    )
}

interface WeatherDataSource {
    fun getWeatherStream(city: String): Flow<WeatherData>
}

data class WeatherData(val temperature: Double)

sealed class WeatherUpdate {
    object Loading : WeatherUpdate()
    data class Success(val city: String, val temperature: Double) : WeatherUpdate()
}
