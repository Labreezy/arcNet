package application

import tornadofx.*

class FontTest : App(Main::class, Styles::class) {
    class Main : View("Font Test") {
        override val root = stackpane {
            label("This is my Label") {
                addClass(Styles.custom)
            }
        }
    }

    class Styles : Stylesheet() {
        companion object {
            val custom by cssclass()
            // Note that loadFont() returns Font?
            val riesling = loadFont("/fonts/riesling.ttf", 48.0)
        }

        init {
            custom {
                padding = box(25.px)

                riesling?.let { font = it }
                // or if you just want to set the font family:
                // riesling?.let { fontFamily = it.family }
            }
        }
    }
}