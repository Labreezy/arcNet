package application

import azUtils.getRes
import javafx.geometry.Pos
import tornadofx.*

class MainStyle : Stylesheet() {

    companion object {
        val fontFiraCodeRegular = loadFont("/fonts/FiraCode-Regular.ttf", 16.0)
        val fontPaladins = loadFont("/fonts/Paladins-Regular.ttf", 16.0)
        val appContainer by cssclass()
        val moduleTitle by cssclass()
        val lobbyName by cssclass()
        val debuggable by cssclass()
    }

    init {
        appContainer { backgroundImage += getRes("gn_background.png") }

        star {
//            /**** Comment out to toggle debug view ****/  borderColor += box(c("#228844DD")); backgroundColor += c("#22664411"); borderWidth += box(1.px); borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.MITER, StrokeLineCap.BUTT, 5.0, 5.0, arrayListOf(1.0))
        }

        label {
            fontFiraCodeRegular?.let { font = it }
            textFill = c("#cccccc")
            fontSize = 14.px

            and(lobbyName) {
                fontPaladins?.let { font = it }
                fontSize = 18.px
                maxWidth = 420.px
                minWidth = 420.px
                maxHeight = 32.px
                minHeight = 32.px
                alignment = Pos.CENTER
            }
            and(moduleTitle) {
                fontFiraCodeRegular?.let { font = it }
                textFill = c("#857d53cc")
                fontSize = 12.px
                maxWidth = 128.px
                minWidth = 128.px
                maxHeight = 32.px
                minHeight = 32.px
                alignment = Pos.CENTER
            }
        }
    }
}
