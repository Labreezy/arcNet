package application

import WINDOW_HORZ
import WINDOW_VERT
import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.*
import org.lwjgl.system.MemoryStack
import session.Player
import session.Session
import window
import imgui.ImGui as Ui
import imgui.WindowFlag as Wf

private val SECTION_FLAGS = Wf.NoTitleBar or Wf.NoCollapse or Wf.NoScrollbar or Wf.NoResize or Wf.NoSavedSettings or Wf.NoFocusOnAppearing or Wf.NoBringToFrontOnFocus
private val CELL_FLAGS = Wf.NoTitleBar or Wf.NoCollapse or Wf.NoScrollbar or Wf.NoResize or Wf.NoSavedSettings
private val session: Session = Session()

fun getSession() = session

fun runApplicationLoop(stack: MemoryStack) {
    // Initialize Application
    if (session.gameLoop == 0) { session.runGameLoop(); print("[${stack.address}]") }
    if (session.xrdApi.isConnected()) { window.title = "gearNet  -  CONNECTED \uD83D\uDCE1"
    } else window.title = "gearNet  -  DISCONNECTED ❌"

    // Top row Function Buttons
    Ui.setNextWindowPos(Vec2(0f,0f), Cond.Always, Vec2(0f))
    Ui.setNextWindowBgAlpha(1.0f)
    Ui.setNextWindowSize(Vec2(WINDOW_HORZ, 40), Cond.Always)
    functionalProgramming.withWindow("Status", null, SECTION_FLAGS) { generateFunctionButtons() }

    // Left column State Monitors
    Ui.setNextWindowPos(Vec2(0f,40f), Cond.Always, Vec2(0F))
    Ui.setNextWindowSize(Vec2(250, WINDOW_VERT), Cond.Always)
    Ui.setNextWindowBgAlpha(0.8f)
    functionalProgramming.withWindow("Debug", null, SECTION_FLAGS) { generateMonitorViews() }

    // Right side Lobby Players
    Ui.setNextWindowPos(Vec2(Ui.io.displaySize[0].toFloat() - 550f, 40), Cond.Always, Vec2(0F))
    Ui.setNextWindowSize(Vec2(550, WINDOW_VERT), Cond.Always)
    Ui.setNextWindowBgAlpha(0.8f); var i = 0
    functionalProgramming.withWindow("Lobby", null, SECTION_FLAGS) {
        session.getAll().forEach { generatePlayerView(it, i++.toFloat()) }
    }
}

private fun generateFunctionButtons() {
    if (session.xrdApi.isConnected()) Ui.textColored(Vec4(0, 1, 0, 0.8), "Xrd ONLINE ${getDots()}")
    else Ui.textColored(Vec4(1, 0, 0, 0.4), "Xrd OFFLINE")
    Ui.sameLine(132)
    if (session.botApi.isConnected()) Ui.textColored(Vec4(0, 1, 0, 0.8), "Bot ONLINE ${getDots()}")
    else Ui.textColored(Vec4(1, 0, 0, 0.4), "Bot OFFLINE")
    Ui.sameLine(256)
    if (session.dataApi.isConnected()) Ui.textColored(Vec4(0, 1, 0, 0.8), "DB ONLINE ${getDots()}")
    else Ui.textColored(Vec4(1, 0, 0, 0.4), "DB OFFLINE")
//    Ui.sameLine(380)
//    Ui.button("Func 4", Vec2(120, 24))
}


private fun generateMonitorViews() {
    Ui.separator()
    Ui.progressBar(0.0f, Vec2(234, 16), "Player 1 HP: n/a")
    Ui.progressBar(0.0f, Vec2(234, 16), "Player 2 HP: n/a")
    Ui.separator()
    Ui.textColored(Vec4(0, 1, 1, 1), "Games Played")
    Ui.sameLine(160)
    Ui.text("${session.gamesCount}")
    Ui.textColored(Vec4(0, 1, 1, 1), "Player Count")
    Ui.sameLine(160)
    Ui.text("${session.getActivePlayerCount()} / ${session.players.size}")
}


fun generatePlayerView(player: Player, height: Float) {
    Ui.setNextWindowPos(Vec2(Ui.io.displaySize[0].toFloat() - 550f, (70f * height) + 40f), Cond.Always, Vec2(0f))
    Ui.setNextWindowSize(Vec2(550f, 70f))
    Ui.setNextWindowBgAlpha(0.8f)
    Ui.pushStyleVar(StyleVar.WindowRounding, 0f)
    functionalProgramming.withWindow("title${height}", null, CELL_FLAGS) {
        Ui.image(2, Vec2(40,52))
        Ui.sameLine(60)
        Ui.beginGroup()
        // Name, & Status
        Ui.textColored(if (player.present) Vec4(0.3,0.8,0.3,1) else Vec4(0.2,0.6,0.2,0.6), player.getNameString())
        Ui.sameLine(230)
        Ui.textColored(statColor(player, (player.getRating()*10).toInt(), Col4.GRAY), "Rating:")
        Ui.sameLine(285)
        Ui.textColored(player.getRatingColor(), player.getRatingLetter())
        Ui.sameLine(325)
        Ui.pushItemWidth(Ui.calcItemWidth()/3)
        if (!player.isIdle()) Ui.progressBar(getLoadBarValue(player),  Vec2(160, 16), getLoadStatusString(player))
        else Ui.progressBar(0f, Vec2(160, 16), "Idle")

        // Character, & Cabinet
        Ui.textColored(statColor(player, player.getIdle(), Vec4(1,1,1,1)), player.getCharacter(false))
        Ui.sameLine(230)
        Ui.textColored(statColor(player, player.getChain(), Col4.GRAY), "Chains:")
        Ui.sameLine(285)
        Ui.textColored(statColor(player, player.getChain(), Vec4(0.2, 1, 0.8, 1)), player.getChainString())
        Ui.sameLine(330)
        if (player.getCabinet().toInt() < 4 && !player.isIdle()) {
            when (player.getData().playerSide.toInt()) {
                0 -> Ui.textColored(Vec4(0.8, 0.2, 0.2, 1), player.getCabinetString())
                1 -> Ui.textColored(Vec4(0.1, 0.5, 0.8, 1), player.getCabinetString())
                2 -> Ui.textColored(Vec4(0.7, 0.6, 0.0, 1), player.getCabinetString())
                else -> Ui.textColored(Col4.GRAY, player.getCabinetString())
            }
        } else Ui.textColored(Col4.GHOST, "-")

        // Bounty, Chain, & Record
        Ui.textColored(statColor(player, player.getBounty(), Col4.GOLD), player.getBountyString())
        Ui.sameLine(224)
        Ui.textColored(if (player.getChange()>0) Col4.GREEN else Col4.RED, player.getChangeString())
        Ui.sameLine(330)
        Ui.textColored(statColor(player, player.getMatchesPlayed(), Col4.GRAY), player.getRecordString())
        Ui.endGroup()
    }
}

private fun statColor(player:Player, value:Int, vec4:Vec4):Vec4 {
    if (!player.present || value <= 0) return Vec4(1,1,1,0.4)
    return vec4
}

private fun getLoadBarValue(player: Player):Float {
    when (player.getLoadPercent()) {
        0 -> return 0f
        100 -> return 0f
        else -> return player.getLoadPercent() * 0.01f
    }
}

private fun getLoadStatusString(player: Player):String {
    when(player.getLoadPercent()) {
        0 -> return "Standby [${player.getIdle()}]"
        100 -> return "Standby [${player.getIdle()}]"
        else -> return "Loading ${player.getLoadPercent()}%"
    }
}

private fun getDots():String {
    when (session.gameLoop) {
        1 -> return "."
        2 -> return ".."
        3 -> return "..."
        4 -> return ""
        else -> session.gameLoop = 1
    }
    return ""
}
