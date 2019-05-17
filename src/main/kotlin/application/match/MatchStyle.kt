package application.match

import javafx.geometry.Pos
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import tornadofx.*

class MatchStyle : Stylesheet() {

    companion object {
        val fontPaladinsStraight = loadFont("/fonts/Paladins-Straight.ttf", 16.0)
        val fontFiraCodeRegular = loadFont("/fonts/FiraCode-Regular.ttf", 16.0)
        val fontFiraCodeBold = loadFont("/fonts/FiraCode-Bold.ttf", 16.0)

        val matchContainer by cssclass()
        val sidestatsContainer by cssclass()
        val matchPlayerTitle by cssclass()
        val matchTitle by cssclass()
        val demoText by cssclass()
    }

    init {
        matchContainer {
            borderWidth += box(2.px)
            borderColor += box(c("#34081c"))
            borderStyle += BorderStrokeStyle(
                StrokeType.INSIDE,
                StrokeLineJoin.ROUND,
                StrokeLineCap.SQUARE,
                10.0,
                0.0,
                arrayListOf(1.0)
            )
            backgroundColor += c("#23041288")
            minWidth = 500.px
            maxWidth = 500.px
            minHeight = 132.px
            maxHeight = 132.px
            alignment = Pos.CENTER
        }

        sidestatsContainer {
            minWidth = 240.px
            maxWidth = 240.px
            minHeight = 120.px
            maxHeight = 120.px
            padding = box(4.px, 4.px, 4.px, 4.px)
            alignment = Pos.TOP_LEFT
        }

        label {
            fontFiraCodeRegular?.let { font = it }
            textFill = c("#cccccc")
            fontSize = 14.px

            and(matchTitle) {
                fontPaladinsStraight?.let { font = it }
                fontSize = 20.px
                textFill = c("#04a4c4")
            }

            and(matchPlayerTitle) {
                fontFiraCodeBold?.let { font = it }
                fontSize = 13.px
                minWidth = 200.px
                maxWidth = 200.px
                backgroundColor += c("#42042299")
                padding = box(0.px, 0.px, 1.px, 3.px)
                textFill = c("#3befaa")
            }

            and(demoText) {
                fontSize = 10.px
                minWidth = 140.px
                maxWidth = 140.px
                padding = box(2.px, 0.px, 0.px, 0.px)
                alignment = Pos.CENTER_LEFT
            }

        }
    }
}