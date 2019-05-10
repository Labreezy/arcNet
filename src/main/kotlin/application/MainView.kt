package application

import azUtils.addCommas
import azUtils.getRes
import azUtils.pathHome
import getSession
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import memscan.PlayerData
import session.Character.getCharacterPortrait
import tornadofx.*
import kotlin.random.Random

class MainController : Controller() {

    fun getPlayerData(): ObservableList<PlayerData> =
        FXCollections.observableArrayList(getSession().xrdApi.getPlayerData())

    fun writeToDb(inputValue: String) {
        println("Writing $inputValue to database!")
    }

}

class MainView : View() {
    val controller: MainController by inject()
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
                    val players = getSession().getAll()
                    for (i in 0..7) {
//                        if (i < players.size) {
                        if (true) {
                            hbox {
                                hbox {
                                    // PLAYER VIEW
                                    addClass(MainStyle.playerContainer)
                                    imageview(getRes("gn_atlas.png").toString()) {
                                        setPreserveRatio(true)
//                                    setViewport(getCharacterPortrait(players[i].getData().characterId))
                                        setViewport(getCharacterPortrait(Random.nextInt(24)))
                                        setPrefSize(64.0, 64.0)
                                    }

                                    vbox {
                                        addClass(MainStyle.playerScoreSection)
                                        label("azeDevs".toUpperCase()) { addClass(MainStyle.playerHandle)
                                            translateX -= 28
                                            translateY -= 2
                                            scaleX -= 0.16
                                        }
//                                    label("EL GRANDE TEJAS HANDLE") { addClass(MainStyle.playerHandle) }
                                        stackpane {
                                            val b = Random.nextInt(999999999).toString()
                                            label("${addCommas(b)} W$") { addClass(MainStyle.playerBounty2)
                                                translateX += 14
                                                translateY += 4
                                                scaleX += 0.05
                                                scaleY += 0.15
                                            }
                                            label("${addCommas(b)} W$") { addClass(MainStyle.playerBounty)
                                                translateX += 14
                                                translateY += 3
                                            }
                                        }

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

                                        //                                progressindicator().progress = Random.nextDouble(1.0)
                                    }
                                }
                                imageview(getRes("gn_atlas.png").toString()) {
//                                    setPreserveRatio(true)
                                    setViewport(Rectangle2D(Random.nextInt(12) * 64.0, 256.0, 64.0, 64.0))
                                    setPrefSize(64.0, 64.0)
                                    scaleX -= 0.20
                                    scaleY -= 0.20
                                    translateX -= 348
                                    translateY += 4
                                }
//                                label("WANTED") {
//                                    translateX -= 460
//                                    translateY -= 20
//                                    rotate -= 8
//                                    addClass(MainStyle.playerWanted)
//
//                                }
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