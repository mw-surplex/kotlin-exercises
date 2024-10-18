package coroutines.flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*

class LocationService(
    locationRepository: LocationRepository,
    backgroundScope: CoroutineScope,
) {
    fun observeLocation(): Flow<Location> = TODO()

    fun currentLocation(): Location? = TODO()
}

interface LocationRepository {
    fun observeLocation(): Flow<Location>
}

data class Location(val latitude: Double, val longitude: Double)

class FakeLocationRepository : LocationRepository {
    private val locationFlow = MutableSharedFlow<Location>()

    override fun observeLocation(): Flow<Location> = locationFlow

    suspend fun emitLocation(location: Location) {
        locationFlow.emit(location)
    }

    fun observersCount(): Int = locationFlow.subscriptionCount.value
}
