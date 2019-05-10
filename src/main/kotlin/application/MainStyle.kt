package application

import azUtils.getRes
import javafx.geometry.Pos
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.FontPosture
import tornadofx.*

class MainStyle : Stylesheet() {

    companion object {
        val fontFiraCodeBold = loadFont("/fonts/FiraCode-Bold.ttf", 16.0)
        val fontFiraCodeLight = loadFont("/fonts/FiraCode-Light.ttf", 16.0)
        val fontFiraCodeMedium = loadFont("/fonts/FiraCode-Medium.ttf", 16.0)
        val fontFiraCodeRegular = loadFont("/fonts/FiraCode-Regular.ttf", 16.0)
        val fontPaladins = loadFont("/fonts/Paladins-Regular.ttf", 16.0)
        val fontPaladinsCond = loadFont("/fonts/Paladins-Condensed.ttf", 16.0)
        val fontPaladinsStraight = loadFont("/fonts/Paladins-Straight.ttf", 16.0)
        val fontRED = loadFont("/fonts/RED.ttf", 16.0)

        val appContainer by cssclass()
        val playerContainer by cssclass()
        val playerScoreSection by cssclass()
        val playerStatsSection by cssclass()
        val playerHandle by cssclass()
        val playerBounty by cssclass()
        val playerBounty2 by cssclass()
        val playerWanted by cssclass()
        val playerRating by cssclass()
        val playerChains by cssclass()
        val moduleOffline by cssclass()
        val moduleOnline by cssclass()
        val moduleWarning by cssclass()
        val lobbyName by cssclass()
        val debuggable by cssclass()
    }

    init {
        appContainer {
            val myURI = getRes("gn_background.png")
            padding = box(0.px)
            spacing = 0.px
            backgroundImage += myURI
        }

        debuggable {
            //            // Comment out to disable
//            borderColor += box(c("#113322CC"))
//            backgroundColor += c("#11332288")
//            borderWidth += box(1.px)
//            borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
        }

        playerContainer {
            borderWidth += box(3.px)
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
            padding = box(2.px)
        }

        playerScoreSection {
            minWidth = 260.px
            padding = box(0.px)
            alignment = Pos.CENTER_LEFT
        }

        playerStatsSection {
            //            minWidth = 150.px
            minWidth = 50.px
            padding = box(0.px)
            alignment = Pos.CENTER_RIGHT
        }

        label {
            fontSize = 14.px
            padding = box(2.px, 4.px)
            textFill = c("#cccccc")

            and(lobbyName) {
                fontPaladins?.let { font = it }
                fontSize = 18.px
                padding = box(-2.px, 0.px, 0.px, 8.px)
                textFill = c("#cccccc")
            }
            and(playerHandle) {
                fontPaladins?.let { font = it }
                fontSize = 18.px
                padding = box(3.px, 0.px, 3.px, 24.px)
                textFill = c("#25dc88")
                fontStyle = FontPosture.ITALIC
                maxWidth = 370.px
                minWidth = 370.px
                backgroundColor += c("#34081c")
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
            and(playerWanted) {
                fontRED?.let { font = it }
                fontSize = 20.px
                padding = box(0.px)
                textFill = c("#cc1a1a")
                maxWidth = 256.px
                minWidth = 256.px
            }
            and(playerRating) {
                fontFiraCodeMedium?.let { font = it }
                fontSize = 12.px
            }
            and(playerChains) {
                fontFiraCodeLight?.let { font = it }
                fontSize = 12.px
            }
            and(moduleOffline) {
                fontFiraCodeBold?.let { font = it }
                fontSize = 12.px
                textFill = c("#71090aee")
                backgroundColor += c("#71090a44")
                borderColor += box(c("#71090a88"))
                borderStyle += BorderStrokeStyle(
                    StrokeType.INSIDE,
                    StrokeLineJoin.ROUND,
                    StrokeLineCap.SQUARE,
                    10.0,
                    0.0,
                    arrayListOf(1.0)
                )
                borderWidth += box(2.px)
                maxWidth = 128.px
                minWidth = 128.px
                maxHeight = 20.px
                minHeight = 20.px
                padding = box(-2.px, 0.px, 0.px, 0.px)
                alignment = Pos.CENTER
            }
            and(moduleOnline) {
                fontFiraCodeBold?.let { font = it }
                fontSize = 12.px
                textFill = c("#04cf03ee")
                backgroundColor += c("#04cf0344")
                borderColor += box(c("#04cf0388"))
                borderStyle += BorderStrokeStyle(
                    StrokeType.INSIDE,
                    StrokeLineJoin.ROUND,
                    StrokeLineCap.SQUARE,
                    10.0,
                    0.0,
                    arrayListOf(1.0)
                )
                borderWidth += box(2.px)
                maxWidth = 128.px
                minWidth = 128.px
                maxHeight = 20.px
                minHeight = 20.px
                padding = box(-2.px, 0.px, 0.px, 0.px)
                alignment = Pos.CENTER
            }
            and(moduleWarning) {
                fontFiraCodeRegular?.let { font = it }
                backgroundColor += c("#222222")
                borderColor += box(c("#fd9a26"))
                borderStyle += BorderStrokeStyle(
                    StrokeType.INSIDE,
                    StrokeLineJoin.ROUND,
                    StrokeLineCap.SQUARE,
                    10.0,
                    0.0,
                    arrayListOf(1.0)
                )
                borderWidth += box(2.px)
            }
        }
    }
}
