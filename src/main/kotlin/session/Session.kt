package session

import database.DatabaseHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import memscan.MemHandler
import memscan.PlayerData
import memscan.XrdApi
import twitch.TwitchBot

class Session {
    val xrdApi: XrdApi = MemHandler()
    val dataApi: DatabaseHandler = DatabaseHandler("159.89.112.213", password = "", username = "")
    val botApi: TwitchBot = TwitchBot("")
    var players: MutableMap<Long, Player> = HashMap()
    var gamesCount: Int = 0
    var memoryCycle = 0
    var databaseCycle = 0

    fun cycleMemoryScan() {
        GlobalScope.launch {
            delay(32)
            if (xrdApi.isConnected()) updatePlayerData()
            memoryCycle++
            cycleMemoryScan()
        }
    }

    fun cycleDatabase() {
        GlobalScope.launch {
            delay(256)
            if (dataApi.isConnected()) updatePlayerLegacies()
            databaseCycle++
            cycleDatabase()
        }
    }

    fun getAll() = players.values.toList()
        .sortedByDescending { item -> item.getRating() }
        .sortedByDescending { item -> item.getBounty() }
        .sortedByDescending { item -> if (!item.isIdle()) 1 else 0 }

    private fun updatePlayerLegacies() {
        // do something
    }

    private fun updatePlayerData() {
        var loserChange = 0
        val playerData = xrdApi.getPlayerData()
        playerData.forEach { data ->
            addPlayerIfNew(data)
            var currLoser = resolveTheLoser(data)
            if (currLoser > 0) loserChange = currLoser
        }
        playerData.forEach { resolveTheWinner(it, loserChange) }
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

    private fun addPlayerIfNew(data: PlayerData) {
        if (!players.containsKey(data.steamUserId) && data.steamUserId != 0L) {
            players[data.steamUserId] = Player(data)
        }
    }

    fun getActivePlayerCount() = players.values.filter { !it.isIdle() }.size

}
