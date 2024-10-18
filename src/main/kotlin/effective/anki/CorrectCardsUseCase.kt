package effective.anki

import effective.anki.AnkiProgressBar.Size.Small
import effective.anki.AnkiCardsRepository
import effective.anki.AnkiDialog
import effective.anki.AnkiProgressBar
import effective.anki.AnkiView

class CorrectCardsUseCase(
    private val view: AnkiView,
    private val cardsRepository: AnkiCardsRepository,
) {

    suspend fun start() {
        val progressBar = AnkiProgressBar(size = Small)
        view.show(progressBar)

        try {
            cardsRepository.correctCards()
        } finally {
            view.hide(progressBar)
        }

        val dialog = AnkiDialog(
            title = "Success",
            text = "Cards correction successful",
            okButton = AnkiDialog.Button("OK"),
        )
        view.show(dialog)
    }
}
