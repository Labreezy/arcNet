package application

import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import tornadofx.Fragment
import tornadofx.addClass
import tornadofx.hbox
import tornadofx.label

class MatchView(override val root: Parent) : Fragment() {

    lateinit var character: Pair<ImageView,ImageView>
    lateinit var handle: Pair<Label,Label>

    init { 
        with(root) {
            hbox { addClass(MatchStyle.matchContainer)
                label("MatchView").addClass(MatchStyle.matchTitle)
            }
        }
    }

//    fun applyMatchSnap(matchData: MatchData) {
//        Platform.runLater({
//
//        })
//    }

}