package application

import javafx.geometry.Pos
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import tornadofx.*

class PlayerStyle : Stylesheet() {

    companion object {
        val fontFiraCodeBold = loadFont("/fonts/FiraCode-Bold.ttf", 16.0)
        val fontFiraCodeMedium = loadFont("/fonts/FiraCode-Medium.ttf", 16.0)
        val fontRED = loadFont("/fonts/RED.ttf", 16.0)

        val playerContainer by cssclass()
        val playerHandle by cssclass()
        val playerBounty by cssclass()
        val playerBounty2 by cssclass()
    }

    init {
        playerContainer {
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
            minWidth = 400.px
            maxWidth = 400.px
            alignment = Pos.TOP_LEFT
        }

        label {
            fontFiraCodeMedium?.let { font = it }
            textFill = c("#cccccc")
            fontSize = 14.px

            and(playerHandle) {
                fontFiraCodeBold?.let { font = it }
                fontSize = 18.px
                textFill = c("#25dc88")
            }
            and(playerBounty) {
                fontRED?.let { font = it }
                fontSize = 32.px
                padding = box(-8.px, 10.px, 4.px, 8.px)
                textFill = c("#ffcc33")
                maxWidth = 256.px
                minWidth = 256.px
            }
            and(playerBounty2) {
                fontRED?.let { font = it }
                fontSize = 32.px
                padding = box(-8.px, 10.px, 4.px, 8.px)
                textFill = c("#9b332cee")
                maxWidth = 256.px
                minWidth = 256.px
            }
        }
    }
}