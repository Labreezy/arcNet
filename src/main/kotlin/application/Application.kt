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
        session.players.values.forEach { generatePlayerView(it, i++.toFloat()) }
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
    Ui.button("Overlay: AUTO", Vec2(234, 32))
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
        Ui.textColored(Vec4(1,0,0,1), player.getNameString())
        Ui.sameLine(295)
        Ui.pushItemWidth(Ui.calcItemWidth()/3)
        Ui.progressBar(player.getLoadPercent() * 0.01f,  Vec2(164, 16), "Standby")

        // Character, & Cabinet
        Ui.text(player.getCharacter(false))
        Ui.sameLine(300)
        Ui.textColored(Vec4(0.64,0.64,0.64,1), player.getCabinetString())

        // Bounty, Chain, & Record
        Ui.textColored(Vec4(1,1,0,1), player.getBountyString())
        Ui.sameLine(200)
        Ui.textColored(Vec4(0,1,1,1), "Chain: ${player.getChain()}")
        Ui.sameLine(300)
        Ui.textColored(Vec4(0.64,0.64,0.64,1), player.getRecordString())
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
