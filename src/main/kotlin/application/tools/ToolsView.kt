package application.tools

import application.main.MainStyle
import azUtils.getRes
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.control.Label
import session.Session
import tornadofx.*

class ToolsView(override val root: Parent) : Fragment() {

    private val modulesGui: MutableList<ModuleView> = ArrayList()
    private lateinit var matchesPlayedLabel: Label
    private lateinit var playersActiveLabel: Label

    init { with(root) {
        stackpane { translateY += 10
            imageview(getRes("gn_atlas.png").toString()) { viewport = Rectangle2D(20.0, 910.0, 920.0, 100.0) }
            hbox {
                addClass(MainStyle.utilsContainer); padding = Insets(10.0,10.0,10.0,15.0)
                minWidth = 920.0
                maxWidth = 920.0
                minHeight = 100.0
                maxHeight = 100.0
                vbox { alignment = Pos.BOTTOM_LEFT
                    vbox {
                        translateY -= 16
                        translateX += 4
                        matchesPlayedLabel = label("Matches:  -")
                        playersActiveLabel = label("Players:  - / -")
                    }
                    hbox {
                        alignment = Pos.BOTTOM_LEFT
                        minWidth = 384.0
                        maxWidth = 384.0
                        hbox { modulesGui.add(ModuleView(parent, "Guilty Gear Xrd")) }
                        hbox { modulesGui.add(ModuleView(parent, "GearNet Client")) }
                        hbox { modulesGui.add(ModuleView(parent, "Stats Database")) }
                    }
                }
            }
        }
        }
    }

    fun applyData(session: Session) = Platform.runLater {
        matchesPlayedLabel.text = "Matches:  ${session.matches}"
        playersActiveLabel.text = "Players:  ${session.getActivePlayerCount()} / ${session.players.size}"
        modulesGui.forEach { it.nextFrame() }
    }

    fun blinkGuiltyGearIndicator(session: Session) {
        modulesGui[0].reset(session.xrdApi.isConnected())
    }

    fun blinkGearNetIndicator(session: Session) {
        modulesGui[1].reset(session.xrdApi.isConnected())
    }

    fun blinkDatabaseIndicator(session: Session) {
        modulesGui[2].reset(session.dataApi.isConnected())
    }

}