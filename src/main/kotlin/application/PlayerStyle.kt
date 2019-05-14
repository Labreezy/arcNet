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
        val fontPaladinsStraight = loadFont("/fonts/Paladins-Straight.ttf", 16.0)
        val fontPaladinsCondensed = loadFont("/fonts/Paladins-Condensed.ttf", 16.0)
        val fontRED = loadFont("/fonts/RED.ttf", 16.0)

        val playerContainer by cssclass()
        val bountyBackdrop by cssclass()
        val statsBackdrop by cssclass()

        val handleText by cssclass()
        val statusBar by cssclass()
        val bountyText by cssclass()
        val bountyShadow by cssclass()
        val changeText by cssclass()
        val statusText by cssclass()
        val recordText by cssclass()
        val chainText by cssclass()
        val chainShadow by cssclass()
    }

    init {
        playerContainer {
            borderWidth += box(2.px)
            borderColor += box(c("#34081c"))
            borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
            backgroundColor += c("#23041288")
            alignment = Pos.TOP_LEFT
            minHeight = 64.px
            maxHeight = 64.px
        }

        bountyBackdrop {
            minWidth = 200.px
            maxWidth = 200.px
            minHeight = 36.px
            maxHeight = 36.px
            backgroundColor += c("#220411aa")
            alignment = Pos.CENTER_LEFT
        }

        statsBackdrop {
            minWidth = 134.px
            maxWidth = 134.px
            minHeight = 36.px
            maxHeight = 36.px
            alignment = Pos.CENTER_LEFT
        }

        statusBar {
            backgroundColor += c("#02627eee")
        }

        label {
            fontFiraCodeMedium?.let { font = it }
            textFill = c("#78cbab")
            fontSize = 10.px

            and(handleText) {
                fontFiraCodeBold?.let { font = it }
                fontSize = 16.px
                textFill = c("#3befaa")
                backgroundColor += c("#42042299")
                padding = box(0.px, 0.px, 1.px, 8.px)
                maxWidth = 335.px
                minWidth = 335.px
                alignment = Pos.CENTER_LEFT
            }
            and(bountyText) {
                fontRED?.let { font = it }
                fontSize = 25.px
                textFill = c("#ffcc33")
                maxWidth = 200.px
                minWidth = 200.px
                alignment = Pos.CENTER_LEFT
            }
            and(bountyShadow) {
                fontRED?.let { font = it }
                fontSize = 25.px
                textFill = c("#9b332acc")
                maxWidth = 200.px
                minWidth = 200.px
                alignment = Pos.CENTER_LEFT
            }
            and(changeText) {
                fontRED?.let { font = it }
                fontSize = 16.px
                maxWidth = 200.px
                minWidth = 200.px
                alignment = Pos.BOTTOM_RIGHT
            }
            and(statusText) {
                padding = box(0.px, 8.px, 0.px, 8.px)
                textFill = c("#120109")
                fontSize = 10.px
                alignment = Pos.CENTER_RIGHT
            }
            and(recordText) {
                fontFiraCodeBold?.let { font = it }
                padding = box(4.px, 0.px, 0.px, 0.px)
                textFill = c("#2bd8a7")
                fontSize = 9.px
                minWidth = 100.px
                maxWidth = 100.px
                alignment = Pos.CENTER_LEFT
            }
            and(chainText) {
                fontPaladinsStraight?.let { font = it }
                fontSize = 26.px
                textFill = c("#16d8d7ee")
                maxWidth = 200.px
                minWidth = 200.px
                alignment = Pos.CENTER
            }
            and(chainShadow) {
                fontPaladinsStraight?.let { font = it }
                fontSize = 24.px
                textFill = c("#0094a4cc")
                maxWidth = 200.px
                minWidth = 200.px
                alignment = Pos.CENTER
            }
        }
    }
}