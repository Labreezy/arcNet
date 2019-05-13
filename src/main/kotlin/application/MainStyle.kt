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
        appContainer {
            padding = box(0.px)
            spacing = 0.px
            backgroundImage += getRes("gn_background.png")
            alignment = Pos.TOP_CENTER
        }

        debuggable {
//            /* Comment out to toggle debug view */ borderColor += box(c("#113322CC")); backgroundColor += c("#11332288"); borderWidth += box(1.px); borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
        }

        label {
            fontSize = 14.px
            padding = box(0.px)
            textFill = c("#cccccc")

            and(lobbyName) {
                fontPaladins?.let { font = it }
                fontSize = 18.px
                maxWidth = 420.px
                minWidth = 420.px
                maxHeight = 32.px
                minHeight = 32.px
                alignment = Pos.CENTER
                textFill = c("#cccccc")
            }
            and(moduleTitle) {
                fontFiraCodeRegular?.let { font = it }
                textFill = c("#857d53cc")
                fontSize = 12.px
                maxWidth = 128.px
                minWidth = 128.px
                maxHeight = 32.px
                minHeight = 32.px
                padding = box(-2.px, 0.px, 0.px, 0.px)
                alignment = Pos.CENTER
            }
        }
    }
}
