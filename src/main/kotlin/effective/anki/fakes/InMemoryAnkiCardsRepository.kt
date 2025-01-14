package effective.anki.fakes

import effective.anki.AnkiCard
import effective.anki.AnkiCardsRepository
import kotlinx.coroutines.delay

class InMemoryAnkiCardsRepository : AnkiCardsRepository {
    var cardsCorrected = false
    var updatedCards: List<AnkiCard>? = null

    override suspend fun updateCards(cards: List<AnkiCard>) {
        delay(1000)
        updatedCards = cards
    }

    override suspend fun correctCards() {
        delay(1000)
        cardsCorrected = true
    }
}
