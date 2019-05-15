package application

import azUtils.addCommas
import azUtils.getRes
import javafx.application.Platform
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.HBox
import session.Character.getCharacterPortrait
import session.Player
import tornadofx.*
import utils.generateRandomName
import kotlin.random.Random

class PlayerView(override val root: Parent) : Fragment() {

    private lateinit var character: ImageView
    private lateinit var statusBar: HBox
    private lateinit var handle: Label
    private lateinit var status: Label

    private lateinit var bounty1: Label
    private lateinit var bounty2: Label

    private lateinit var chain1: Label
    private lateinit var chain2: Label

    private lateinit var change: Label

    private lateinit var record: Label
    private lateinit var cabinet: Label
    private lateinit var location: Label

    init { with(root) {
        hbox { addClass(PlayerStyle.playerContainer)
            minWidth = 400.0
            maxWidth = 400.0
            character = imageview(getRes("gn_atlas.png").toString()) {
                viewport = Rectangle2D(576.0, 192.0, 64.0, 64.0)
                translateY -= 2.0
                translateX -= 2.0
            }
            vbox {
                translateX -= 4.0
                minWidth = 340.0
                maxWidth = 340.0
                stackpane { alignment = Pos.CENTER_LEFT
                    statusBar = hbox { addClass(PlayerStyle.statusBar)
                        translateY += 1.0
                        maxWidth = 0.0
                    }
                    hbox {
                        handle = label("") {
                            addClass(PlayerStyle.handleText)
                            translateY += 1.0
                        }
                        status = label("") {
                            addClass(PlayerStyle.statusText)
                            translateX -= 160.0
                            translateY += 4.0
                            maxWidth = 160.0
                            minWidth = 160.0
                        }
                    }
                }

                hbox {
                    vbox { addClass(PlayerStyle.bountyBackdrop)
                        translateY += 2.0
                        stackpane {
                            translateX += 10.0
                            translateY -= 5.0
                            bounty2 = label("") { addClass(PlayerStyle.bountyShadow)
                                scaleX += 0.05
                                scaleY += 0.20
                            }
                            bounty1 = label("") { addClass(PlayerStyle.bountyText) }
                        }
                    }
                    vbox {
                        stackpane {
                            translateX -= 64.0
                            chain2 = label("") { addClass(PlayerStyle.chainShadow)
                                translateY += 6.0
                                scaleY += 0.20
                            }
                            chain1 = label("") { addClass(PlayerStyle.chainText)
                                translateY += 6.0
                                scaleX -= 0.20
                            }
                        }
                    }
                    vbox { addClass(PlayerStyle.statsBackdrop)
                        translateX -= 142.0
                        translateY += 2.0
                        record = label("") { addClass(PlayerStyle.recordText) }
                        cabinet = label("") { addClass(PlayerStyle.recordText) }
                        location = label("") { addClass(PlayerStyle.recordText) }
                    }
                    vbox { padding = Insets(0.0,0.0,0.0,8.0)
                        translateX -= 525.0
                        translateY += 5.0
                        change = label("") { addClass(PlayerStyle.changeText) }
                    }
                }
            }
        }
    } }

    fun applyData(p: Player) = Platform.runLater {
        if (false) applyRandomData(p) else
            if (p.getSteamId() > 0L) {

                character.viewport = getCharacterPortrait(p.getData().characterId, p.isIdle())

                handle.text = p.getNameString(); handle.isVisible = true
                statusBar.maxWidth = 335.0 * (p.getLoadPercent()*0.01)
                status.text = p.getStatusString()

                bounty1.text = p.getBountyString()
                bounty2.text = p.getBountyString()

                chain1.text = p.getChainString()
                chain2.text = p.getChainString()

                if (p.getChange() > 0) change.textFill = c("#84c928") else change.textFill = c("#d22e44")
                change.text = p.getChangeString()

                record.text = p.getRecordString()
                cabinet.text = p.getCabinetString()
                location.text = p.getPlaySideString()

            } else {
                character.viewport = Rectangle2D(576.0, 192.0, 64.0, 64.0)
                handle.text = ""; handle.isVisible = false
                statusBar.maxWidth = 0.0
                status.text = ""
                bounty1.text = ""
                bounty2.text = ""
                chain1.text = ""
                chain2.text = ""
                change.text = ""
                record.text = ""
                cabinet.text = ""
                location.text = ""
            }
    }

    private fun applyRandomData(p: Player) {
        val bountyStr = addCommas(Random.nextInt(1222333).toString())
        val loadingInt = Random.nextInt(100)
        val changeInt = Random.nextInt(-444555, 666777)
        val chainInt = Random.nextInt(0, 9)
        val winsInt = Random.nextInt(44)
        character.viewport = Rectangle2D(Random.nextInt(8) * 64.0, Random.nextInt(4) * 64.0, 64.0, 64.0)
        handle.text = generateRandomName()
        statusBar.maxWidth = 335.0 * (loadingInt * 0.01)
        bounty1.text = "$bountyStr W$"
        bounty2.text = "$bountyStr W$"
        chain1.text = if (chainInt >= 8) "★" else if (chainInt > 0) chainInt.toString() else ""
        chain2.text = if (chainInt >= 8) "★" else if (chainInt > 0) chainInt.toString() else ""
        if (changeInt > 0) change.textFill = c("#84c928") else change.textFill = c("#d22e44")
        change.text = p.getChangeString(1f, changeInt)
        record.text = "W:$winsInt  /  M:${winsInt + Random.nextInt(44)}"
        location.text = "Roaming Lobby"
        status.text = "Standby: ${Random.nextInt(1, 8)} [$loadingInt%]"
    }

}