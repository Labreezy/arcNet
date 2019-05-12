package session

import application.ModuleGui
import application.PlayerGui
import database.DatabaseHandler
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import memscan.MemHandler
import memscan.PlayerData
import memscan.XrdApi
import tornadofx.Controller


class Session: Controller() {
    val modulesApi: MutableList<ModuleGui> = ArrayList(listOf(ModuleGui(), ModuleGui(), ModuleGui()))
    val guiApi: MutableList<PlayerGui> = ArrayList()
    val xrdApi: XrdApi = MemHandler()
    val dataApi: DatabaseHandler = DatabaseHandler("159.89.112.213", password = "", username = "")
    val players: HashMap<Long, Player> = HashMap()
    var gamesCount: Int = 0

    fun cycleMemoryScan() {
        GlobalScope.launch {
            modulesApi.get(0).reset(xrdApi.isConnected())
            if (xrdApi.isConnected() && updatePlayerData()) updateAppUi()
            delay(512)
            cycleMemoryScan()
        }
    }

    fun cycleDatabase() {
        GlobalScope.launch {
            modulesApi.get(2).reset(dataApi.isConnected())
            delay(4096)
            cycleDatabase()
        }
    }

    private fun updatePlayerData(): Boolean {
        var somethingChanged = false
        var loserChange = 0
        val playerData = xrdApi.getPlayerData()
        playerData.forEach { data ->
            if (!players.containsKey(data.steamUserId) && data.steamUserId != 0L) {
                players.put(data.steamUserId, Player(data))
                somethingChanged = true
            }
            var currLoser = resolveTheLoser(data)
            if (currLoser > 0) {
                loserChange = currLoser
                somethingChanged = true
            }
        }
        playerData.forEach { resolveTheWinner(it, loserChange) }
        return somethingChanged
    }

    private fun resolveTheWinner(data: PlayerData, loserChange: Int) {
        players.values.forEach { s ->
            if (s.getSteamId() == data.steamUserId) {
                if (s.hasWon()) {
                    gamesCount++
                    s.changeChain(1)
                    s.changeBounty(s.getChain()*s.getMatchesWon()+s.getMatchesPlayed()+loserChange + (s.getChain() * s.getChain() * 100))
                }
            }
        }
    }

    private fun resolveTheLoser(data: PlayerData): Int {
        var loserChange = 0
        players.values.forEach { s ->
            if (s.getSteamId() == data.steamUserId) {
                s.updatePlayerData(data)
                if (s.hasLost()) {
                    players.values.forEach { if (!it.hasPlayed()) it.incrementIdle() }
                    s.changeChain(-1)
                    if (s.getBounty() > 0) loserChange = s.getBounty().div(3)
                    s.changeBounty(-loserChange)
                    return loserChange
                }
            }
        }
        return 0
    }

    fun getAll(): ObservableList<Player> = FXCollections.observableArrayList(players.values.toList()
        .sortedByDescending { item -> item.getRating() }
        .sortedByDescending { item -> item.getBounty() }
        .sortedByDescending { item -> if (!item.isIdle()) 1 else 0 })


    fun updateAppUi() {
        modulesApi.get(1).reset(true)
        val uiUpdate: List<Player> = getAll()
        for (i in 0..7) if (uiUpdate.size > i) guiApi.get(i).applyData(uiUpdate.get(i))
    }

}
