package session

import database.DatabaseHandler
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import memscan.MemHandler
import memscan.XrdApi
import twitch.TwitchBot

class Session {
    val xrdApi: XrdApi = MemHandler()
    val dataApi: DatabaseHandler = DatabaseHandler("", "")
    val botApi: TwitchBot = TwitchBot("")
    var players: MutableMap<Long, Player> = HashMap()
    var gamesCount: Int = 0
    var gameLoop = 0

    fun runGameLoop() {
        gameLoop++
        GlobalScope.launch {
            delay(16)
            if (xrdApi.isConnected()) updatePlayerData()
            runGameLoop()
        }
    }

    fun getAll() = players.values.toList()
        .sortedByDescending { item -> item.getRating() }
        .sortedByDescending { item -> item.getBounty() }
        .sortedByDescending { item -> if (!item.isIdle()) 1 else 0 }

    fun updatePlayerData() {
        var loserChange = 0
        val playerData = xrdApi.getPlayerData()
        playerData.forEach { data ->
            // Add new player if they didn't previously exist
            if (!players.containsKey(data.steamUserId) && data.steamUserId != 0L) {
                players.put(data.steamUserId, Player(data))
            }

            // Resolve changes to the loser
            players.values.forEach { s ->
                if (s.getSteamId() == data.steamUserId) {
                    s.updatePlayerData(data)
                    if (s.hasLost()) {
                        players.values.forEach { if (!it.hasPlayed()) it.incrementIdle() }
                        s.changeChain(-1)
                        if (s.getBounty() > 0) loserChange = ((s.getChain() * s.getChain() * 10) + s.getBounty()).div(2)
                        s.changeBounty(-loserChange)
                    }
                }
            }
        }
        playerData.forEach { data ->
            // Resolve changes to the winner
            players.values.forEach { s ->
                if (s.getSteamId() == data.steamUserId) {
                    if (s.hasWon()) {
                        gamesCount++
                        s.changeChain(1)
                        s.changeBounty(loserChange + (s.getChain() * s.getChain() * 100))
                    }
                }
            }
        }
    }

    fun getActivePlayerCount() = players.values.filter { !it.isIdle() }.size

}