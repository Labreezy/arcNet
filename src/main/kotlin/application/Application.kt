package application

import bounties.Player
import glm_.vec2.Vec2
import glm_.vec4.Vec4
import imgui.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import memscan.MemHandler
import memscan.PlayerData
import memscan.XrdApi
import window
import imgui.ImGui as Ui
import imgui.WindowFlag as Wf

private val xrdApi: XrdApi = MemHandler()
private val players: MutableMap<Long, Player> = HashMap()
var gameLoop = 0

fun runApplicationLoop() {
    var i = 0
    if (gameLoop == 0) {
        players.put(0L, Player(PlayerData(0L,"ggplayer0",0x0,0x0,0x0,0,0,0)))
        players.put(1L, Player(PlayerData(1L,"ggplayer1",0x0,0x0,0x0,0,0,0)))
        players.put(2L, Player(PlayerData(2L,"ggplayer2",0x0,0x0,0x0,0,0,0)))
        players.put(3L, Player(PlayerData(3L,"ggplayer3",0x0,0x0,0x0,0,0,0)))

        runGameLoop()
    }
    if (xrdApi.isConnected()) { window.title = "gearNet  -  CONNECTED \uD83D\uDCE1 ${getDots()}"
    } else window.title = "gearNet  -  DISCONNECTED ❌ ${getDots()}"
    generateMonitorViews()
    players.values.forEach { generatePlayerView(it, i++.toFloat()) }
}

private fun generateMonitorViews() {
//    Ui.button("test", Vec2(5))

    val windowPos = Vec2(0f)
    val windowPosPivot = Vec2(0f)
    Ui.setNextWindowPos(windowPos, Cond.Always, windowPosPivot)
    Ui.setNextWindowContentSize(Vec2(200, 24))
    Ui.setNextWindowBgAlpha(0.8f)
    Ui.pushStyleVar(StyleVar.WindowRounding, 4f)

    functionalProgramming.withWindow("Debug", null, Wf.NoTitleBar or Wf.NoResize or Wf.NoSavedSettings or Wf.NoFocusOnAppearing or Wf.NoNav) {
        Ui.textColored(Vec4(0, 1, 1, 1), "Player Count")
        Ui.sameLine(160)
        Ui.text("${players.size}")
    }
}

private fun getDots():String {
    when (gameLoop) {
        1 -> return "."
        2 -> return ".."
        3 -> return "..."
        4 -> return ""
        else -> gameLoop = 1
    }
    return ""
}

private fun runGameLoop() {
    gameLoop++
    GlobalScope.launch {
        delay(256)
        if (xrdApi.isConnected()) { players.values.forEach { player -> xrdApi.getPlayerData().forEach { data -> if (data.steamUserId == player.getSteamId()) player.updatePlayerData(data) } } }
        runGameLoop()
    }
}

fun generatePlayerView(player: Player, height: Float) {
    val windowPos = Vec2(Ui.io.displaySize[0].toFloat() - 444f, (94f * height) + 16f)
    val windowPosPivot = Vec2(0f)
    Ui.setNextWindowPos(windowPos, Cond.Always, windowPosPivot)
    Ui.setNextWindowContentSize(Vec2(420f, 88f))
    Ui.setNextWindowBgAlpha(0.8f)
    Ui.pushStyleVar(StyleVar.WindowRounding, 0f)

    functionalProgramming.withWindow("title${height}", null, Wf.NoTitleBar or Wf.NoResize or Wf.NoSavedSettings or Wf.NoFocusOnAppearing or Wf.NoNav) {
        Ui.textColored(Vec4(1,0,0,1), player.getNameString())
        Ui.separator()

        Ui.textColored(Vec4(1,1,0,1), player.getCharacter(false))
        Ui.pushItemWidth(Ui.calcItemWidth()/3)
        Ui.sameLine(200)
        Ui.pushItemWidth(Ui.calcItemWidth()/3)
        Ui.progressBar(player.getLoadPercent() * 0.01f)

        Ui.text(player.getRecordString())
        Ui.sameLine(200)
        Ui.text(player.getBountyString())

        Ui.text(player.getCabinetString())
        Ui.sameLine(200)
        Ui.text(player.getPlaySideString())
    }
}
