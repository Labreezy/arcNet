package application

import azUtils.getRes
import javafx.beans.property.SimpleStringProperty
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import session.Character.getCharacterPortrait
import session.Session
import tornadofx.*

class MainView : View() {
    val session: Session = Session()
    val input = SimpleStringProperty()

    override val root: Form = Form()

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
                    label("Guilty Gear Xrd") {
                        if (session.xrdApi.isConnected()) addClass(MainStyle.moduleOnline) else addClass(
                            MainStyle.moduleOffline
                        )
                    }
                    label("GearNet") { addClass(MainStyle.moduleOnline) }
                    label("Database") {
                        if (session.dataApi.isConnected()) addClass(MainStyle.moduleOnline) else addClass(
                            MainStyle.moduleOffline
                        )
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
                    hbox {
                        vbox {
                            addClass(MainStyle.debuggable)
                            label("Rating: A") { addClass(MainStyle.playerRating) }
                            label("Chains: 8") { addClass(MainStyle.playerChains) }
                        }
                        vbox {
                            addClass(MainStyle.debuggable)
                            label("Wins: 80") { addClass(MainStyle.playerRating) }
                            label("Games: 160") { addClass(MainStyle.playerChains) }
                        }
                    }
                    label("Warning Box!") {
                        addClass(MainStyle.moduleWarning)
                    }
                }
                vbox {
                    addClass(MainStyle.debuggable)
                    setPadding(Insets(0.0, 16.0, 0.0, 16.0))
                    setSpacing(4.0)
                    minWidth = 420.0
                    maxWidth = 420.0
                    // PLAYER VIEWS
                    children.bind(session.leaderboard) {
                        hbox {
                            hbox {
                                addClass(MainStyle.playerContainer)
                                imageview(getRes("gn_atlas.png").toString()) {
                                    setPreserveRatio(true)
                                    setViewport(getCharacterPortrait(it.getData().characterId))
                                    setPrefSize(64.0, 64.0)
                                }

                                vbox {
                                    addClass(MainStyle.playerScoreSection)
                                    label(it.getNameString()) { addClass(MainStyle.playerHandle)
                                        translateX -= 28
                                        translateY -= 2
                                        scaleX -= 0.16
                                    }
                                    stackpane {
                                        label(it.getBountyFormatted(1f)) { addClass(MainStyle.playerBounty2)
                                            translateX += 14
                                            translateY += 4
                                            scaleX += 0.05
                                            scaleY += 0.15
                                        }
                                        label(it.getBountyFormatted(1f)) { addClass(MainStyle.playerBounty)
                                            translateX += 14
                                            translateY += 3
                                        }
                                    }

                                }
                                vbox {
                                    addClass(MainStyle.playerStatsSection)
                                    progressbar(it.getLoadPercent()*0.01) {
                                        minWidth = 80.0
                                        maxHeight = 15.0
                                        translateY -= 20
                                    }
                                }
                            }
                            imageview(getRes("gn_atlas.png").toString()) {
                                setViewport(Rectangle2D(it.getChain() * 64.0, 256.0, 64.0, 64.0))
                                setPrefSize(64.0, 64.0)
                                scaleX -= 0.20
                                scaleY -= 0.20
                                translateX -= 348
                                translateY += 4
                            }
                        }
                    }
                }
            }
        }
        session.cycleMemoryScan()
    }
}