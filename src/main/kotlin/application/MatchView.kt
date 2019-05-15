package application

import azUtils.getRes
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import tornadofx.*


class MatchView(override val root: Parent) : Fragment() {

    lateinit var character: Pair<ImageView,ImageView>
    lateinit var handle: Pair<Label,Label>

    init { with(root) {
            stackpane {
                imageview(getRes("gn_atlas.png").toString()) { setViewport(Rectangle2D(6.0, 768.0, 500.0, 128.0)) }
                hbox {
                    addClass(MatchStyle.matchContainer)
                    label("MatchView").addClass(MatchStyle.matchTitle)
                }
            }
        }
    }

    fun applyMatchSnap() = Platform.runLater {
        TODO("not implemented")
    }

}