package application

import azUtils.getRes
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import memscan.MatchData
import tornadofx.*

/*
    data class MatchData(
        //val players: Pair<PlayerData, PlayerData>, TBA, maybe yoink steam id through login + DB or something

        val tension: Pair<Int, Int> = Pair(-1,-1),
        val health: Pair<Int, Int> = Pair(-1,-1),
        val burst: Pair<Boolean, Boolean> = Pair(false,false),
        val risc: Pair<Int, Int> = Pair(-1,-1),
        val isHit: Pair<Boolean, Boolean> = Pair(false,false)

        // val beats: Pair<Int, Int>,
        // val timer: Int
        // Connection? : Int
        // Score marks? : Pair<Int, Int>
        // Damage taken? : Pair<Int, Int>
        // Button(s) pressed? : Pair<?, ?>
        // Direction pressed? : Pair<?, ?>
        // Tension Pulse? : Pair<Float, Float>
        // Stun level? : Pair<Int, Int>
    )
*/

class MatchView(override val root: Parent) : Fragment() {

    lateinit var character: Pair<ImageView,ImageView>
    lateinit var handle: Pair<Label,Label>

    init { 
        with(root) {
            stackpane {
                imageview(getRes("gn_atlas.png").toString()) { setViewport(Rectangle2D(6.0, 768.0, 500.0, 128.0)) }
                hbox {
                    addClass(MatchStyle.matchContainer)
                    label("MatchView").addClass(MatchStyle.matchTitle)
                }
            }
        }
    }

    fun applyMatchSnap(matchData: MatchData) {
        Platform.runLater({

        })
    }

}