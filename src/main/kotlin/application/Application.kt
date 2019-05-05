package application

import application.Playerviews.generatePlayerView
import application.Topbar.generateFunctionButtons
import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.*
import org.lwjgl.system.MemoryStack
import session.Session
import window
import imgui.ImGui as Ui
import imgui.WindowFlag as Wf

val WINDOW_TINT = Vec4(0.26f, 0.06f, 0.16f, 1.0f)
val WINDOW_HORZ = 800
val WINDOW_VERT = 600
private val SECTION_FLAGS = Wf.NoTitleBar or Wf.NoCollapse or Wf.NoScrollbar or Wf.NoResize or Wf.NoSavedSettings or Wf.NoFocusOnAppearing or Wf.NoBringToFrontOnFocus
private val session: Session = Session()

fun getSession() = session

fun runApplicationLoop(stack: MemoryStack) {
    // Initialize Application
    if (session.databaseCycle == 0) { session.cycleDatabase(); print("[${stack.address}] ") }
    if (session.memoryCycle == 0) { session.cycleMemoryScan() }
    if (session.xrdApi.isConnected()) { window.title = "gearNet  -  CONNECTED \uD83D\uDCE1"
    } else window.title = "gearNet  -  DISCONNECTED ❌"

    // Top row Function Buttons
    Ui.setNextWindowPos(Vec2(0f,0f), Cond.Always, Vec2(0f))
    Ui.setNextWindowBgAlpha(1.0f)
    Ui.setNextWindowSize(Vec2(WINDOW_HORZ, 40), Cond.Always)
    functionalProgramming.withWindow("Status", null, SECTION_FLAGS) { generateFunctionButtons(session) }

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





