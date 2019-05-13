package application

import javafx.geometry.Pos
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import tornadofx.*

class MatchStyle : Stylesheet() {

    companion object {
        val fontPaladinsStraight = loadFont("/fonts/Paladins-Straight.ttf", 16.0)

        val matchContainer by cssclass()
        val matchTitle by cssclass()
        val chartChill by cssclass()
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

        chartChill {
            backgroundColor += c("#00000000")
        }

        label {
            MainStyle.fontFiraCodeRegular?.let { font = it }
            textFill = c("#cccccc")
            fontSize = 14.px

            and(matchTitle) {
                fontPaladinsStraight?.let { font = it }
                fontSize = 18.px
                textFill = c("#471129cc")
            }
        }
    }
}