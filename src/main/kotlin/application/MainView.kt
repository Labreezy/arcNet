package application

import azUtils.getRes
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import session.Character.getCharacterPortrait
import session.Player
import session.Session
import tornadofx.*


class MainView : View() {
    override val root: Form = Form()

    val session: Session by inject()

    fun animateFrames() {
        GlobalScope.launch {
            delay(64)
            session.modulesApi.forEach { it.nextFrame() }
            animateFrames()
        }
    }

    init {
        with(root) {
            addClass(MainStyle.appContainer)
            // TOP BAR
            hbox {
                alignment = Pos.CENTER
                maxHeight = 16.0
                setPadding(Insets(12.0, 0.0, 12.0, 0.0))
                hbox {
                    addClass(MainStyle.debuggable)
                    setSpacing(8.0)
                    minWidth = 520.0
                    alignment = Pos.CENTER
                    stackpane {
                        session.modulesApi.get(0).backdrop = imageview(getRes("gn_atlas.png").toString()) {
                            if (session.xrdApi.isConnected()) addClass(MainStyle.moduleTitle) else addClass(MainStyle.moduleTitle)
                            setViewport(Rectangle2D(512.0, if (session.xrdApi.isConnected())  256.0 else 288.0, 128.0, 32.0))
                            setPrefSize(128.0, 32.0)
                        }
                        session.modulesApi.get(0).moduleName = label("Guilty Gear Xrd") {
                            if (session.xrdApi.isConnected()) addClass(MainStyle.moduleTitle) else addClass(MainStyle.moduleTitle)
                        }
                    }
                    stackpane { addClass(MainStyle.debuggable)
                        session.modulesApi.get(1).backdrop = imageview(getRes("gn_atlas.png").toString()) { addClass(MainStyle.debuggable)
                            setViewport(Rectangle2D(512.0, 256.0, 128.0, 32.0))
                            setPrefSize(128.0, 32.0)
                        }
                        session.modulesApi.get(1).moduleName = label("GearNet Client") { addClass(MainStyle.moduleTitle, MainStyle.debuggable) }
                    }
                    stackpane {
                        session.modulesApi.get(2).backdrop = imageview(getRes("gn_atlas.png").toString()) {
                            if (session.dataApi.isConnected()) addClass(MainStyle.moduleTitle) else addClass(MainStyle.moduleTitle)
                            setViewport(Rectangle2D(512.0, if (session.dataApi.isConnected())  256.0 else 288.0, 128.0, 32.0))
                            setPrefSize(128.0, 32.0)
                        }
                        session.modulesApi.get(2).moduleName = label("Stats Database") {
                            if (session.dataApi.isConnected()) addClass(MainStyle.moduleTitle) else addClass(MainStyle.moduleTitle)
                        }
                    }
                }
                hbox {
                    addClass(MainStyle.debuggable)
                    minWidth = 420.0
                    alignment = Pos.CENTER_RIGHT
                    setPadding(Insets(0.0, 32.0, 0.0, 0.0))
                    label("LOBBY_MAX_LENGTH") { addClass(MainStyle.lobbyName) }
                }
            }
            hbox {
                alignment = Pos.CENTER
                vbox {
                    addClass(MainStyle.debuggable)
                    // SIDE INFO
                    setPadding(Insets(0.0, 16.0, 16.0, 16.0))
                    minWidth = 520.0
                    maxWidth = 520.0
                }
                vbox {
                    addClass(MainStyle.debuggable)
                    setPadding(Insets(0.0, 16.0, 0.0, 16.0))
                    setSpacing(4.0)
                    minWidth = 420.0
                    maxWidth = 420.0
                    // PLAYER VIEWS
                    for (i in 0..7) {
                        session.guiApi.add(PlayerGui())
                        hbox {
                            hbox {
                                addClass(MainStyle.playerContainer)
                                session.guiApi.get(i).character = imageview(getRes("gn_atlas.png").toString()) {
                                    setViewport(Rectangle2D(576.0, 192.0, 64.0, 64.0))
                                    setPrefSize(64.0, 64.0)
                                }

                                vbox {
                                    addClass(MainStyle.playerScoreSection)
                                    session.guiApi.get(i).handle = label("") {
                                        addClass(MainStyle.playerHandle)
                                        translateX -= 28
                                        translateY -= 2
                                        scaleX -= 0.16
                                    }
                                }
                            }
                        }
                    }
                }
            }
            session.cycleMemoryScan()
            session.cycleDatabase()
            animateFrames()
        }

    }

}

class PlayerGui {
    var player = Pair(Player(), Player())
    lateinit var character: ImageView
    lateinit var handle: Label

    fun applyData(playerUpdate: Player) {
        Platform.runLater({
            this.player = Pair(this.player.second, playerUpdate)
            if (player.second.getSteamId() != -1L) {
                if (player.first.getCharacterId() != player.second.getCharacterId()) character.setViewport(getCharacterPortrait(player.second.getData().characterId))
                if (!player.first.getNameString().equals(player.second.getNameString())) handle.text = player.second.getNameString()
            } else {
                character.setViewport(Rectangle2D(576.0, 192.0, 64.0, 64.0))
                handle.text = ""
            }
        })
    }
}

class ModuleGui {
    lateinit var backdrop: ImageView
    lateinit var moduleName: Label
    private var frame = 0
    private var enabled = true
    fun nextFrame() {
        Platform.runLater({
            if (frame++ > 4) frame = 4
            if (enabled) moduleName.textFill = c("#8ddf4fee")
            else moduleName.textFill = c("#c14e2eee")
            when (frame) {
                1 ->    backdrop.setViewport(Rectangle2D(512.0, if (enabled) 256.0 else 288.0, 128.0, 32.0))
                2 ->    backdrop.setViewport(Rectangle2D(640.0, if (enabled) 256.0 else 288.0, 128.0, 32.0))
                3 ->    backdrop.setViewport(Rectangle2D(768.0, if (enabled) 256.0 else 288.0, 128.0, 32.0))
                4 ->    backdrop.setViewport(Rectangle2D(896.0, if (enabled) 256.0 else 288.0, 128.0, 32.0))
                else -> backdrop.setViewport(Rectangle2D(896.0, if (enabled) 256.0 else 288.0, 128.0, 32.0))
            }
        })
    }
    fun reset(connected:Boolean) {
        frame = 0
        enabled = connected
    }
}