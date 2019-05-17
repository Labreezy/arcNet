package application.match

import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.layout.StackPane
import session.Character.getCharacterPortrait
import session.Match
import tornadofx.*
import utils.Duo
import utils.getRes
import utils.isInRange

/*
    data class MatchData(
        val tension: Pair<Int, Int> = Pair(-1,-1),
        val health: Pair<Int, Int> = Pair(-1,-1),
        val burst: Pair<Boolean, Boolean> = Pair(false,false),
        val risc: Pair<Int, Int> = Pair(-1,-1),
        val isHit: Pair<Boolean, Boolean> = Pair(false,false)
    )
*/
class MatchView(override val root: Parent) : Fragment() {

    private val P1 = 0
    private val P2 = 1
    var playerSteamId = Duo(-1L, -1L)

    var wholeThing = StackPane()
    var timer = Label()
    var cabinet = Label()
    var character = Duo(ImageView(),ImageView())
    var handle = Duo(Label(),Label())
    var tension = Duo(Label(),Label())
    var health = Duo(Label(),Label())
    var burst = Duo(Label(),Label())
    var risc = Duo(Label(),Label())
    var isHit = Duo(Label(),Label())


    fun applyMatch(m: Match) = Platform.runLater {
        wholeThing.opacity = 1.0
        timer.text = m.getTimer().toString()
        cabinet.text = m.getCabinetString()

        character.p1.setViewport(getCharacterPortrait(m.getCharacter(P1)))
        handle.p1.text = m.getHandleString(P1)
        tension.p1.text = m.getTensionString(P1)
        health.p1.text = m.getHealthString(P1)
        burst.p1.text = m.getBurstString(P1)
        risc.p1.text = m.getRiscString(P1)
        isHit.p1.text = m.getHitStunString(P1)

        character.p2.setViewport(getCharacterPortrait(m.getCharacter(P2)))
        handle.p2.text = m.getHandleString(P2)
        tension.p2.text = m.getTensionString(P2)
        health.p2.text = m.getHealthString(P2)
        burst.p2.text = m.getBurstString(P2)
        risc.p2.text = m.getRiscString(P2)
        isHit.p2.text = m.getHitStunString(P2)

        if (isInRange(m.getTension(P1), 0, 10000)) tension.p1.textFill = c("#84c928") else tension.p1.textFill = c("#d22e44")
        if (isInRange(m.getHealth(P1), 0, 420)) health.p1.textFill = c("#84c928") else health.p1.textFill = c("#d22e44")
        if (isInRange(m.getRisc(P1), -12800, 12800)) risc.p1.textFill = c("#84c928") else risc.p1.textFill = c("#d22e44")

        if (isInRange(m.getTension(P2), 0, 10000)) tension.p2.textFill = c("#84c928") else tension.p2.textFill = c("#d22e44")
        if (isInRange(m.getHealth(P2), 0, 420)) health.p2.textFill = c("#84c928") else health.p2.textFill = c("#d22e44")
        if (isInRange(m.getRisc(P2), -12800, 12800)) risc.p2.textFill = c("#84c928") else risc.p2.textFill = c("#d22e44")

        isHit.p1.textFill = c("#84c928")
        burst.p1.textFill = c("#84c928")
        isHit.p2.textFill = c("#84c928")
        burst.p2.textFill = c("#84c928")

    }

    init {
        with(root) {
            wholeThing = stackpane { opacity = 0.4
                imageview(getRes("gn_atlas.png").toString()) { setViewport(Rectangle2D(6.0, 768.0, 500.0, 128.0)) }
                cabinet = label {
                    addClass(MatchStyle.matchTitle)
                    translateY -= 45.0
                }
                hbox {
                    addClass(MatchStyle.matchContainer)

                    vbox { addClass(MatchStyle.sidestatsContainer)
                        translateY += 35.0
                        hbox {
                            character.p1 = imageview(getRes("gn_atlas.png").toString()) {
                                viewport = Rectangle2D(576.0, 192.0, 64.0, 64.0)
                                fitHeight = 32.0
                                fitWidth = 32.0
                                translateX -= 3.0
                                translateY -= 2.0
                            }
                            vbox {
                                handle.p1 = label().addClass(MatchStyle.matchPlayerTitle)
                                health.p1 = label().addClass(MatchStyle.demoText)
                            }
                        }
                        hbox{ translateY += 6.0
                            risc.p1 = label().addClass(MatchStyle.demoText)
                            isHit.p1 = label().addClass(MatchStyle.demoText)
                        }
                        hbox { translateY += 6.0
                            tension.p1 = label().addClass(MatchStyle.demoText)
                            burst.p1 = label().addClass(MatchStyle.demoText)
                        }
                    }
                    vbox { addClass(MatchStyle.sidestatsContainer)
                        translateY += 35.0
                        translateX += 4.0
                        hbox {
                            character.p2 = imageview(getRes("gn_atlas.png").toString()) {
                                viewport = Rectangle2D(576.0, 192.0, 64.0, 64.0)
                                fitHeight = 32.0
                                fitWidth = 32.0
                                translateX -= 3.0
                                translateY -= 2.0
                            }
                            vbox {
                                handle.p2 = label().addClass(MatchStyle.matchPlayerTitle)
                                health.p2 = label().addClass(MatchStyle.demoText)
                            }
                        }
                        hbox{ translateY += 6.0
                            risc.p2 = label().addClass(MatchStyle.demoText)
                            isHit.p2 = label().addClass(MatchStyle.demoText)
                        }
                        hbox { translateY += 6.0
                            tension.p2 = label().addClass(MatchStyle.demoText)
                            burst.p2 = label().addClass(MatchStyle.demoText)
                        }
                    }
                }
            }
        }
    }

}