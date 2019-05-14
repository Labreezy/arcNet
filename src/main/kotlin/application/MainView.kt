package application

import azUtils.getRes
import javafx.geometry.Insets
import javafx.geometry.Pos
import javafx.geometry.Rectangle2D
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import session.Player
import session.Session
import tornadofx.*

var globalSession:Session? = null

class MainView : View() {
    override val root: Form = Form()
    val modulesGui: MutableList<ModuleView> = ArrayList()
    val playersGui: MutableList<PlayerView> = ArrayList()
    val matchesGui: MutableList<MatchView> = ArrayList()

    val session: Session by inject()

    fun cycleDatabase() { GlobalScope.launch {
        modulesGui.get(2).reset(session.dataApi.isConnected())
        delay(2048); cycleDatabase() } }

    fun cycleMemScan() { GlobalScope.launch {
        modulesGui.get(0).reset(session.xrdApi.isConnected())
        if (session.xrdApi.isConnected() && session.updatePlayers()) redrawAppUi()
        delay(256); cycleMemScan() } }

    fun cycleUi() { GlobalScope.launch {
        modulesGui.forEach { it.nextFrame() }
        delay(64); cycleUi() } }

    fun redrawAppUi() {
        modulesGui.get(1).reset(true)
        val uiUpdate: List<Player> = session.players.values.toList().sortedByDescending { item -> item.getRating() }.sortedByDescending { item -> item.getBounty() }.sortedByDescending { item -> if (!item.isIdle()) 1 else 0 }
        for (i in 0..7) if (uiUpdate.size > i) playersGui.get(i).applyData(uiUpdate.get(i))
        else playersGui.get(i).applyData(Player()) }

    init {
        globalSession = session
        with(root) { addClass(MainStyle.appContainer)
            translateY -= 5.0
            hbox {

                // ======== LEFT SIDE COLUMN ========
                vbox { alignment = Pos.TOP_CENTER; setSpacing(2.0)
                    minWidth = 520.0
                    maxWidth = 520.0

                    // MATCH INFO
                    label("MATCH MONITORS") { addClass(MainStyle.lobbyName) }
                    // MATCH VIEWS
                    for (i in 0..3) hbox { matchesGui.add(MatchView(parent)) }

                }

                // ======== RIGHT SIDE COLUMN ========
                vbox { alignment = Pos.TOP_CENTER; setSpacing(2.0)
                    minWidth = 420.0
                    maxWidth = 420.0

                    // LOBBY NAME
                    label("LOBBY_TITLE_FULL") { addClass(MainStyle.lobbyName) }
                    // PLAYER VIEWS
                    for (i in 0..7) hbox { playersGui.add(PlayerView(parent)) }
                }

            }

            // ======== BOTTOM UTILS ========
            hbox { alignment = Pos.BOTTOM_CENTER
                minWidth = 940.0
                maxWidth = 940.0
                minHeight = 120.0
                maxHeight = 120.0
                stackpane { translateY += 10
                    imageview(getRes("gn_atlas.png").toString()) { setViewport(Rectangle2D(20.0, 910.0, 920.0, 100.0)) }
                    hbox {
                        addClass(MainStyle.utilsContainer); setPadding(Insets(10.0,10.0,10.0,15.0))
                        minWidth = 920.0
                        maxWidth = 920.0
                        minHeight = 100.0
                        maxHeight = 100.0
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

            cycleDatabase()
            cycleMemScan()
            cycleUi()
        }

    }

}



