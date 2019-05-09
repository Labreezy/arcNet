package application

import azUtils.pathHome
import javafx.geometry.Pos
import javafx.scene.layout.BorderStrokeStyle
import javafx.scene.shape.StrokeLineCap
import javafx.scene.shape.StrokeLineJoin
import javafx.scene.shape.StrokeType
import javafx.scene.text.FontPosture
import javafx.scene.text.FontWeight
import tornadofx.*
import tornadofx.Stylesheet.Companion.box
import java.net.URI

class MainStyle : Stylesheet() {
    companion object {
        val appContainer by cssclass()
        val playerContainer by cssclass()
        val playerScoreSection by cssclass()
        val playerStatsSection by cssclass()
        val playerHandle by cssclass()
        val playerBounty by cssclass()
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
            val myURI = URI("${pathHome.toUri().toURL()}src/main/resources/gn_background.png")
            padding = box(0.px)
            spacing = 0.px
            backgroundImage += myURI
        }

        debuggable {
//            borderColor += box(c("#113322CC"))
//            backgroundColor += c("#11332288")
//            borderWidth += box(1.px)
//            borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
        }

        playerContainer {
            borderWidth += box(3.px)
            borderColor += box(c("#34081c"))
            borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
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
                fontSize = 18.px
                padding = box(-2.px, 0.px, 0.px, 8.px)
                textFill = c("#cccccc")
                fontWeight = FontWeight.BOLD
                fontFamily = "Paladins"
            }
            and(playerHandle) {
//                rotate = 1.deg
                fontSize = 18.px
                padding = box(-2.px, 0.px, 0.px, 8.px)
                textFill = c("#33ff33")
                fontStyle = FontPosture.ITALIC
                fontWeight = FontWeight.BOLD
                maxWidth = 256.px
                minWidth = 256.px
            }
            and(playerBounty) {
//                rotate = -1.deg
                fontSize = 32.px
                padding = box(-8.px, 10.px, 4.px, 8.px)
                textFill = c("#ffcc33")
                fontFamily = "RED"
                maxWidth = 256.px
                minWidth = 256.px
            }
            and(playerRating) {
                fontSize = 12.px
            }
            and(playerChains) {
                fontSize = 12.px
            }
            and(moduleOffline) {
                fontSize = 12.px
                textFill = c("#71090aee")
                backgroundColor += c("#71090a44")
                borderColor += box(c("#71090a88"))
                borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
                borderWidth += box(2.px)
                maxWidth = 128.px
                minWidth = 128.px
                maxHeight = 20.px
                minHeight = 20.px
                padding = box(-2.px, 0.px, 0.px, 0.px)
                alignment = Pos.CENTER
                fontWeight = FontWeight.BOLD
            }
            and(moduleOnline) {
                fontSize = 12.px
                textFill = c("#04cf03ee")
                backgroundColor += c("#04cf0344")
                borderColor += box(c("#04cf0388"))
                borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
                borderWidth += box(2.px)
                maxWidth = 128.px
                minWidth = 128.px
                maxHeight = 20.px
                minHeight = 20.px
                padding = box(-2.px, 0.px, 0.px, 0.px)
                alignment = Pos.CENTER
                fontWeight = FontWeight.BOLD
            }
            and(moduleWarning) {
                backgroundColor += c("#222222")
                borderColor += box(c("#fd9a26"))
                borderStyle += BorderStrokeStyle(StrokeType.INSIDE, StrokeLineJoin.ROUND, StrokeLineCap.SQUARE, 10.0, 0.0, arrayListOf(1.0))
                borderWidth += box(2.px)
            }
        }
    }
}