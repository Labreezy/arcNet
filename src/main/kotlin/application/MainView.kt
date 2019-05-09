package application

import azUtils.addCommas
import azUtils.pathHome
import getSession
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import tornadofx.*
import kotlin.concurrent.thread
import kotlin.random.Random

class MainView : View() {
//    val controller: MainController by inject()
//    val input = SimpleStringProperty()

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
                        if (getSession().xrdApi.isConnected()) addClass(MainStyle.moduleOnline) else addClass(
                            MainStyle.moduleOffline
                        )
                    }
                    label("GearNet") { addClass(MainStyle.moduleOnline) }
                    label("Database") {
                        if (getSession().dataApi.isConnected()) addClass(MainStyle.moduleOnline) else addClass(
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
                    // SIDE INFO SOMETHING ???
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
                    label("it's a label") {
                        addClass(MainStyle.debuggable)
                        text = getSession().xrdApi.isConnected().toString()
                    }
                }
                vbox {
                    addClass(MainStyle.debuggable)
                    setPadding(Insets(0.0, 16.0, 0.0, 16.0))
                    setSpacing(4.0)
                    minWidth = 420.0
                    maxWidth = 420.0
                    for (i in 0..7) {
                        hbox {
                            // PLAYER VIEW
                            addClass(MainStyle.playerContainer)
                            imageview("${pathHome.toUri().toURL()}src/main/resources/gn_atlas.png") {
                                setPreserveRatio(true)
                                setViewport(Rectangle2D(Random.nextInt(15) * 64.0, Random.nextInt(3) * 64.0, 64.0, 64.0))
                                setPrefSize(64.0, 64.0)
                            }
                            vbox {
                                addClass(MainStyle.playerScoreSection)
                                label("EL GRANDE TEJAS HANDLE") { addClass(MainStyle.playerHandle) }
                                label("${addCommas(Random.nextInt(999999999).toString())} W$") { addClass(MainStyle.playerBounty) }
                            }
                            vbox {
                                addClass(MainStyle.playerStatsSection)
//                                hbox {
//                                    progressbar(Random.nextDouble(1.0)) {
//                                        minWidth = 100.0
//                                        maxHeight = 16.0
//                                    }
//                                }
//                                hbox {
//                                    vbox {
//                                        addClass(MainStyle.debuggable)
//                                        label("Rating: A") { addClass(MainStyle.playerRating) }
//                                        label("Chains: 8") { addClass(MainStyle.playerChains) }
//                                    }
//                                    vbox {
//                                        addClass(MainStyle.debuggable)
//                                        label("Wins: 80") { addClass(MainStyle.playerRating) }
//                                        label("Games: 160") { addClass(MainStyle.playerChains) }
//                                    }
//
//                                }
                                imageview("${pathHome.toUri().toURL()}src/main/resources/gn_atlas.png") {
                                    setPreserveRatio(true)
                                    setViewport(Rectangle2D(Random.nextInt(15) * 64.0, 256.0, 64.0, 64.0))
                                    setPrefSize(64.0, 64.0)
                                }
//                                progressindicator().progress = Random.nextDouble(1.0)
                            }
                        }
                    }
                }
            }
        }
    }
}

class PlayerView : View() {
    override val root: Form = form {
        hbox {
            // PLAYER VIEW
            addClass(MainStyle.playerContainer)
            imageview("${pathHome.toUri().toURL()}src/main/resources/gn_atlas.png") {
                setPreserveRatio(true)
                setViewport(Rectangle2D(0.0, 0.0, 64.0, 64.0))
                setPrefSize(64.0, 64.0)
            }
            vbox {
                addClass(MainStyle.playerScoreSection)
                label("EL GRANDE TEJAS HANDLE") { addClass(MainStyle.playerHandle) }
                label("2,876,050 W$") { addClass(MainStyle.playerBounty) }
            }
            vbox {
                addClass(MainStyle.playerStatsSection)
                hbox {
                    progressbar {
                        minWidth = 120.0
                        maxHeight = 16.0
                    }
                }
                hbox {
                    vbox {
                        label("Rating: A") { addClass(MainStyle.playerRating) }
                        label("Chains: 8") { addClass(MainStyle.playerChains) }
                    }
                    vbox {
                        label("Wins: 80") { addClass(MainStyle.playerRating) }
                        label("Games: 160") { addClass(MainStyle.playerChains) }
                    }
                }
            }
        }
    }
}