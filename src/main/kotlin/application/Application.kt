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
val session: Session = Session()

fun runApplicationLoop(stack: MemoryStack) {
    // Initialize Application
    if (session.gameLoop == 0) { session.runGameLoop(); print("[${stack.address}]") }
    if (session.xrdApi.isConnected()) { window.title = "gearNet  -  CONNECTED \uD83D\uDCE1 ${getDots()}"
    } else window.title = "gearNet  -  DISCONNECTED ❌ ${getDots()}"

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
    Ui.setNextWindowPos(Vec2(Ui.io.displaySize[0].toFloat() - 470f, 40), Cond.Always, Vec2(0F))
    Ui.setNextWindowSize(Vec2(470, WINDOW_VERT), Cond.Always)
    Ui.setNextWindowBgAlpha(0.8f); var i = 0
    functionalProgramming.withWindow("Lobby", null, SECTION_FLAGS) {
        session.getAll().forEach { generatePlayerView(it, i++.toFloat()) }
    }
}

private fun generateFunctionButtons() {
    Ui.button("Func 1", Vec2(120, 24))
    Ui.sameLine(132)
    Ui.button("Func 2", Vec2(120, 24))
    Ui.sameLine(256)
    Ui.button("Func 3", Vec2(120, 24))
    Ui.sameLine(380)
    Ui.button("Func 4", Vec2(120, 24))
}


private fun generateMonitorViews() {
    Ui.separator()
    Ui.progressBar(0.0f, Vec2(234, 16), "Player 1 HP: n/a")
    Ui.progressBar(0.0f, Vec2(234, 16), "Player 2 HP: n/a")
    Ui.separator()
    Ui.textColored(Vec4(0, 1, 1, 1), "Player Count")
    Ui.sameLine(160)
    Ui.text("${session.players.size}")
}



fun generatePlayerView(player: Player, height: Float) {
    Ui.setNextWindowPos(Vec2(Ui.io.displaySize[0].toFloat() - 470f, (70f * height) + 40f), Cond.Always, Vec2(0f))
    Ui.setNextWindowSize(Vec2(470f, 70f))
    Ui.setNextWindowBgAlpha(0.8f)
    Ui.pushStyleVar(StyleVar.WindowRounding, 0f)
    functionalProgramming.withWindow("title${height}", null, CELL_FLAGS) {
        // Name, & Status
        if (player.present) Ui.textColored(Vec4(1,0.2,0,1), player.getNameString())
        else Ui.textColored(Vec4(0.4,0.4,0.4,1), player.getNameString())
        Ui.sameLine(200)
        Ui.textColored(Vec4(0.2,1,0.8,1), "Rating: ${player.getRatingLetter()}")
        Ui.sameLine(295)
        Ui.pushItemWidth(Ui.calcItemWidth()/3)
        if (player.present) Ui.progressBar(getLoadBarValue(player),  Vec2(164, 16), getLoadStatusString(player))
        else Ui.progressBar(0f, Vec2(164, 16), "Offline")

        // Character, & Cabinet
        if (player.present) Ui.text(player.getCharacter(false))
        else Ui.textColored(Vec4(0.4,0.4,0.4,1), player.getCharacter(false))
        Ui.sameLine(300)
        Ui.textColored(Vec4(0.64,0.64,0.64,1), player.getCabinetString())

        // Bounty, Chain, & Record
        if (player.present) Ui.textColored(Vec4(1,0.8,0.2,1), player.getBountyString())
        else Ui.textColored(Vec4(0.4,0.4,0.4,1), player.getBountyString())
        Ui.sameLine(200)
        if (player.present) Ui.textColored(Vec4(0.2,1,0.8,1), player.getChainString())
        else Ui.textColored(Vec4(0.4,0.4,0.4,1), player.getChainString())
        Ui.sameLine(300)
        Ui.textColored(Vec4(0.64,0.64,0.64,1), player.getRecordString())
    }
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
        0 -> return "Standby"
        100 -> return "Standby"
        else -> return "${player.getLoadPercent()}% Loaded"
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
