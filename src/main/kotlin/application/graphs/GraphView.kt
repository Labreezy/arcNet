package application.graphs

import application.match.MatchStyle
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import tornadofx.*
import utils.getRes

/*
    data class MatchData(
        val tension: Pair<Int, Int> = Pair(-1,-1),
        val health: Pair<Int, Int> = Pair(-1,-1),
        val burst: Pair<Boolean, Boolean> = Pair(false,false),
        val risc: Pair<Int, Int> = Pair(-1,-1),
        val isHit: Pair<Boolean, Boolean> = Pair(false,false)
    )
*/

class GraphView(override val root: Parent) : Fragment() {

    init { with(root) {
            stackpane {
                imageview(getRes("gn_atlas.png").toString()) { setViewport(Rectangle2D(6.0, 768.0, 500.0, 128.0)) }
                label("GRAPH") { addClass(MatchStyle.matchTitle) }
                }
            }
    }

    fun appendGraphData() = Platform.runLater {
        TODO("not implemented")
    }

}