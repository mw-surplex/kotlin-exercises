package effective.design

class DeckConnector(
    val deckName: String
) : Comparable<DeckConnector> {
    var state: ConnectionState = ConnectionState.Initial

    override fun compareTo(other: DeckConnector): Int {
        TODO("Not yet implemented")
    }

    enum class ConnectionState {
        Initial,
        Connected,
        Disconnected
    }
}
