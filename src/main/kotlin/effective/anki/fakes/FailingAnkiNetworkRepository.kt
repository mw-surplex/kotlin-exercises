package effective.anki.fakes

import effective.anki.AnkiApiException
import effective.anki.AnkiCard
import effective.anki.AnkiNetworkRepository
import effective.anki.AnkiUser

class FailingAnkiNetworkRepository(
    private val exception: AnkiApiException
) : AnkiNetworkRepository {

    override suspend fun fetchCards(): List<AnkiCard> = throw exception

    override suspend fun fetchUser(): AnkiUser? = throw exception
}
