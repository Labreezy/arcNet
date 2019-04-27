package application

import bounties.Player
import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.Cond
import imgui.ImGui
import imgui.functionalProgramming
import imgui.or
import memscan.MemHandler
import memscan.XrdApi
import window
import imgui.WindowFlag as Wf

val WINDOW_NAME = "gearNet  -  DISCONNECTED ❌ ..."
val WINDOW_TINT = Vec4(0.16f, 0.16f, 0.16f, 1.0f)
val WINDOW_HORZ = 640
val WINDOW_VERT = 480
val yup = true

private val xrdApi: XrdApi = MemHandler()
private val players: List<Player> = ArrayList()

fun runApplicationLoop() {
    ImGui.setNextWindowPos(Vec2(0, 0), Cond.Always)
    ImGui.setNextWindowSize(Vec2(WINDOW_HORZ, WINDOW_VERT), Cond.Always)
    ImGui.begin("", null, 0 or Wf.NoTitleBar or Wf.NoResize or Wf.NoCollapse or Wf.NoBackground)
    ImGui.end()

    if (xrdApi.isConnected()) { window.title = "gearNet  -  CONNECTED \uD83D\uDCE1 ..."
        val xrdData = xrdApi.getPlayerData()
        for (i in 0..xrdData.size-1) {
            if (xrdData.get(i).steamUserId != 0L) generatePlayerView(Player(xrdData.get(i)), i.toFloat())
        }
    } else window.title = WINDOW_NAME


}

fun generatePlayerView(player: Player, height: Float) {
    val windowPos = Vec2(ImGui.io.displaySize[0].toFloat() - 440f, (96f * height) + 10f)
    val windowPosPivot = Vec2(0f)
    ImGui.setNextWindowPos(windowPos, Cond.Always, windowPosPivot)
    ImGui.setNextWindowContentSize(Vec2(420f, 88f))
    ImGui.setNextWindowBgAlpha(0.8f)

    functionalProgramming.withWindow("title${height}", null, Wf.NoTitleBar or Wf.NoResize or Wf.NoSavedSettings or Wf.NoFocusOnAppearing or Wf.NoNav) {
        ImGui.text(player.getNameString())
        ImGui.separator()
        ImGui.pushItemWidth(ImGui.fontSize * -12)

        ImGui.textColored(Vec4(1.0f, 0.0f, 0.0f, 1.0f), player.getCharacter(false))
        ImGui.sameLine(200)
        ImGui.progressBar(player.getLoadPercent() * 0.01f)

        ImGui.text(player.getRecordString())
        ImGui.sameLine(200)
        ImGui.text(player.getBountyString())

        ImGui.text(player.getCabinetString())
        ImGui.sameLine(200)
        ImGui.text(player.getPlaySideString())
    }
//    ImGui.begin("", null, flags)
//
//    ImGui.text(player.getNameString())
//    ImGui.separator()
//    ImGui.pushItemWidth(ImGui.fontSize * -12)
//
//    ImGui.text(player.getCharacter(false))
//    ImGui.sameLine(200)
//    ImGui.progressBar(player.getLoadPercent() * 0.01f)
//
//    ImGui.text(player.getRecordString())
//    ImGui.sameLine(200)
//    ImGui.text(player.getBountyString())
//
//    ImGui.text(player.getCabinetString())
//    ImGui.sameLine(200)
//    ImGui.text(player.getPlaySideString())
//
//    ImGui.end()
}
