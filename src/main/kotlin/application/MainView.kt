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
        with(root) { addClass(MainStyle.appContainer)

            hbox { addClass(MainStyle.debuggable); alignment = Pos.TOP_CENTER
                setPadding(Insets(16.0, 0.0, 0.0, 0.0))
                vbox { addClass(MainStyle.debuggable); alignment = Pos.TOP_CENTER; setSpacing(4.0)
                    minWidth = 520.0
                    maxWidth = 520.0
                    // MODULE INFO
                    hbox { addClass(MainStyle.debuggable); alignment = Pos.CENTER
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
                        stackpane {
                            session.modulesApi.get(1).backdrop = imageview(getRes("gn_atlas.png").toString()) {
                                setViewport(Rectangle2D(512.0, 256.0, 128.0, 32.0))
                                setPrefSize(128.0, 32.0)
                            }
                            session.modulesApi.get(1).moduleName = label("GearNet Client") { addClass(MainStyle.moduleTitle) }
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
                    // MATCH VIEWS
                    hbox { addClass(MainStyle.matchContainer)
                        label("MatchView 0").addClass(MainStyle.matchTitle)
                    }
                    hbox { addClass(MainStyle.matchContainer)
                        label("MatchView 1").addClass(MainStyle.matchTitle)
                    }
                    hbox { addClass(MainStyle.matchContainer)
                        label("MatchView 2").addClass(MainStyle.matchTitle)
                    }
                    hbox { addClass(MainStyle.matchContainer)
                        label("MatchView 3").addClass(MainStyle.matchTitle)
                    }

                }
                vbox { addClass(MainStyle.debuggable); alignment = Pos.TOP_CENTER; setSpacing(4.0)
                    // LOBBY INFO
                    hbox {
                        addClass(MainStyle.debuggable)
                        label("LOBBY_MAX_LENGTH") { addClass(MainStyle.lobbyName) }
                    }
                    // PLAYER VIEWS
                    for (i in 0..7) {
                        session.guiApi.add(PlayerGui())
                        hbox { addClass(MainStyle.debuggable)
                            addClass(MainStyle.playerContainer)
                            session.guiApi.get(i).character = imageview(getRes("gn_atlas.png").toString()) {
                                setViewport(Rectangle2D(576.0, 192.0, 64.0, 64.0))
                                setPrefSize(64.0, 64.0)
                            }
                            vbox {
                                session.guiApi.get(i).handle = label("") {
                                    addClass(MainStyle.playerHandle)
                                    translateY += 2.0
                                    translateX += 8.0
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
    lateinit var character: ImageView
    lateinit var handle: Label

    fun applyData(playerUpdate: Player) {
        Platform.runLater({
            character.setViewport(getCharacterPortrait(playerUpdate.getData().characterId))
            handle.text = playerUpdate.getNameString()
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