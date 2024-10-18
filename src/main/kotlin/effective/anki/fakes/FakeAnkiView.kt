package effective.anki.fakes

import effective.anki.AnkiView
import effective.anki.AnkiViewElement

class FakeAnkiView : AnkiView {
    var visibleElements = listOf<AnkiViewElement>()

    override fun show(element: AnkiViewElement) {
        visibleElements = visibleElements + element
    }

    override fun hide(element: AnkiViewElement) {
        visibleElements = visibleElements - element
    }
}
