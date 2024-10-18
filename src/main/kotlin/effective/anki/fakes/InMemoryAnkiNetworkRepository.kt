package effective.anki.fakes

import effective.anki.AnkiCard
import effective.anki.AnkiNetworkRepository
import effective.anki.AnkiUser

class InMemoryAnkiNetworkRepository(
    private val cards: List<AnkiCard> = emptyList(),
    private val user: AnkiUser? = null
) : AnkiNetworkRepository {

    override suspend fun fetchCards(): List<AnkiCard> = cards

    override suspend fun fetchUser(): AnkiUser? = user
}
