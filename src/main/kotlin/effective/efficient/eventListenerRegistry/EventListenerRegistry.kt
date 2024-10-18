package effective.efficient.eventListenerRegistry

import java.util.concurrent.ConcurrentHashMap

class EventListenerRegistry<E> {
    private val listeners = ConcurrentHashMap
        .newKeySet<EventListener<E>>()

    fun addEventListener(
        event: E,
        handler: () -> Unit
    ): EventListener<E> {
        val listener = EventListener(event, handler)
        listeners += listener
        return listener
    }

    fun invokeListeners(event: E) {
        listeners
            .filter { it.event == event && it.isActive }
            .forEach { it.handleEvent() }
    }
}

class EventListener<E>(
    val event: E,
    val handler: () -> Unit,
) {
    var isActive: Boolean = true
        private set

    fun handleEvent() {
        handler()
    }

    fun cancel() {
        isActive = false
    }
}

enum class Event { A, B, C }
