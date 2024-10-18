package coroutines.flow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.concurrent.atomic.AtomicInteger

// Produces a flow of Unit
// For instance producingUnits(5) -> [Unit, Unit, Unit, Unit, Unit]
fun producingUnits(num: Int): Flow<Unit> = TODO()

// Adds a delay of time `timeMillis` between elements
fun <T> Flow<T>.delayEach(timeMillis: Long): Flow<T> = TODO()

// Should transform values, where transformation value should have index of the element
// flowOf("A", "B").mapIndexed { index, value -> "$index$value" } -> ["0A", "0B"]
fun <T, R> Flow<T>.mapIndexed(transformation: suspend (index: Int, T) -> R): Flow<R> = TODO()

// Should transform Unit's to next numbers starting from 1
// For instance flowOf(Unit, Unit, Unit, Unit).toNextNumbers() -> [1, 2, 3, 4]
// Example:
// Input   --------U------UU---------U------
// Result  --------1------23---------4------
fun Flow<*>.toNextNumbers(): Flow<Int> = TODO()

// Produces not only elements, but the whole history till now
// For instance flowOf(1, "A", 'C').withHistory() -> [[], [1], [1, A], [1, A, C]]
fun <T> Flow<T>.withHistory(): Flow<List<T>> = TODO()

// Based on two light switches, should decide if the general light should be switched on.
// Should be if one is true and another is false.
// The first state should be false, and then with each even from any switch, new state should be emitted.
// Example:
// switch1 -------t-----f----------t-t-------
// switch2 ----------------f-t-f--------t-f-t
// Result  f------t-----f--f-t-f---t-t--f-t-f
fun makeLightSwitch(switch1: Flow<Boolean>, switch2: Flow<Boolean>): Flow<Boolean> = TODO()

// Based on two light switches, should decide if the general light should be switched on.
// Should be if one is turned on and another is off
// Each event from a switch emits news state. The first state is true, each new state is toggled.
// Example:
// switch1 -------U-----U--------------------------U---------------UU-----
// switch2 ----------------U-------------------U-------------U------------
// Result  -------t-----f--t-------------------f---t---------f-----tf-----
fun makeLightSwitchToggle(switch1: Flow<Unit>, switch2: Flow<Unit>): Flow<Boolean> = TODO()

fun polonaisePairing(track1: Flow<Person>, track2: Flow<Person>): Flow<Pair<Person, Person>> = TODO()

data class Person(val name: String)
