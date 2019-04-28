package session

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import memscan.MemHandler
import memscan.XrdApi

class Session {
    val xrdApi: XrdApi = MemHandler()
    var players: MutableMap<Long, Player> = HashMap()
    var gamesCount: Int = 0
    var gameLoop = 0

    fun runGameLoop() {
        gameLoop++
        GlobalScope.launch {
            delay(256)
            if (xrdApi.isConnected()) updatePlayerData()
            runGameLoop()
        }
    }

    fun getAll() = players.values.toList()
        .sortedByDescending { item -> item.getRating() }
        .sortedByDescending { item -> item.getBounty() }
        .sortedByDescending { item -> item.present }

    fun updatePlayerData() {
        var loserChange = 0
        // reset all data to be updated
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
                    if (s.justLost()) {
                        players.values.forEach { if (!it.justPlayed()) it.incrementIdle() }
                        s.changeChain(-1)
                        if (s.getBounty() > 0) loserChange = ((s.getChain() * s.getChain() * 10) + s.getBounty()).div(2)
                        s.changeBounty(-loserChange)
                        println("justLost: ${-loserChange} W$")
                    }
                }
            }
        }
        playerData.forEach { data ->
            // Resolve changes to the winner
            players.values.forEach { s ->
                if (s.getSteamId() == data.steamUserId) {
                    if (s.justWon()) {
                        gamesCount++
                        s.changeChain(1)
                        s.changeBounty(loserChange + (s.getChain() * s.getChain() * 100))
                        println("justWon: ${loserChange} W$")
                    }
                }
            }
        }
    }

}