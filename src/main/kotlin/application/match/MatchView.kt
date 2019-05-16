package application.match

import azUtils.getRes
import javafx.application.Platform
import javafx.geometry.Rectangle2D
import javafx.scene.Parent
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import session.Duo
import session.Match
import tornadofx.*
import utils.generateRandomName
import kotlin.random.Random

/*
    data class MatchData(
        val tension: Pair<Int, Int> = Pair(-1,-1),
        val health: Pair<Int, Int> = Pair(-1,-1),
        val burst: Pair<Boolean, Boolean> = Pair(false,false),
        val risc: Pair<Int, Int> = Pair(-1,-1),
        val isHit: Pair<Boolean, Boolean> = Pair(false,false)
    )
*/
class MatchView(override val root: Parent, val cabLetter: String) : Fragment() {

    private var matches = ArrayList<Match>()
    var character = Duo<ImageView, ImageView>()
    var handle = Duo<Label, Label>()

    init {
        with(root) {
            stackpane {
                imageview(getRes("gn_atlas.png").toString()) { setViewport(Rectangle2D(6.0, 768.0, 500.0, 128.0)) }
                label("CABINET ${cabLetter}") {
                    addClass(MatchStyle.matchTitle)
                    translateY -= 45.0
                }
                hbox {
                    addClass(MatchStyle.matchContainer)

                    vbox { addClass(MatchStyle.sidestatsContainer)
                        translateY += 35.0
                        hbox {
                            character.p1 = imageview(getRes("gn_atlas.png").toString()) {
                                viewport = Rectangle2D(Random.nextInt(8) * 64.0, Random.nextInt(4) * 64.0, 64.0, 64.0)
                                fitHeight = 32.0
                                fitWidth = 32.0
                                translateX -= 3.0
                                translateY -= 2.0
                            }
                            vbox {
                                label(generateRandomName()).addClass(MatchStyle.matchPlayerTitle)
                                label("Health: 0 / 420").addClass(MatchStyle.demoText)
                            }
                        }
                        hbox{ translateY += 6.0
                            label("   RISC: 0 / 12800").addClass(MatchStyle.demoText)
                            label("  IsHit: FALSE").addClass(MatchStyle.demoText)
                        }
                        hbox { translateY += 6.0
                            label("Tension: 0 / 10000").addClass(MatchStyle.demoText)
                            label("  Burst: FALSE").addClass(MatchStyle.demoText)
                        }
                    }
                    vbox { addClass(MatchStyle.sidestatsContainer)
                        translateY += 35.0
                        translateX += 4.0
                        hbox {
                            character.p2 = imageview(getRes("gn_atlas.png").toString()) {
                                viewport = Rectangle2D(Random.nextInt(8) * 64.0, Random.nextInt(4) * 64.0, 64.0, 64.0)
                                fitHeight = 32.0
                                fitWidth = 32.0
                                translateX -= 3.0
                                translateY -= 2.0
                            }
                            vbox {
                                label(generateRandomName()).addClass(MatchStyle.matchPlayerTitle)
                                label("Health: 0 / 420").addClass(MatchStyle.demoText)
                            }
                        }
                        hbox{ translateY += 6.0
                            label("   RISC: 0 / 12800").addClass(MatchStyle.demoText)
                            label("  IsHit: FALSE").addClass(MatchStyle.demoText)
                        }
                        hbox { translateY += 6.0
                            label("Tension: 0 / 10000").addClass(MatchStyle.demoText)
                            label("  Burst: FALSE").addClass(MatchStyle.demoText)
                        }
                    }
                }
            }
        }
    }

    fun applyMatchSnap() = Platform.runLater {
        TODO("not implemented")
    }

}