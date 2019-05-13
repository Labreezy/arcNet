package application

import azUtils.getRes
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import session.Character.getCharacterPortrait
import session.Player
import tornadofx.*

class PlayerView(override val root: Parent) : Fragment() {

    lateinit var character: ImageView
    lateinit var handle: Label

    init { with(root) {
        hbox { addClass(PlayerStyle.playerContainer)
            character = imageview(getRes("gn_atlas.png").toString()) {
                setViewport(Rectangle2D(576.0, 192.0, 64.0, 64.0))
                translateY -= 2.0
                translateX -= 2.0
            }
            vbox {
                minWidth = 100.0
                maxWidth = 100.0
                handle = label("") {
                    addClass(PlayerStyle.playerHandle)
                    translateY += 2.0
                    translateX += 8.0
                }
            }
        }
    } }

    fun applyData(playerUpdate: Player) {
        Platform.runLater({
            character.setViewport(getCharacterPortrait(playerUpdate.getData().characterId))
            handle.text = playerUpdate.getNameString()
        })
    }

}