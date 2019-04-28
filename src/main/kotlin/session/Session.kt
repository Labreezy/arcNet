package session

import database.LegacyData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import memscan.MemHandler
import memscan.XrdApi

class Session {
    val xrdApi: XrdApi = MemHandler()
    var players: MutableMap<Long, Player> = HashMap()
    private var gamesCount: Int = 0
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
        xrdApi.getPlayerData().forEach { data ->
            // Add new player if they didn't previously exist
            if (!players.containsKey(data.steamUserId) && data.steamUserId != 0L) {
                players.put(data.steamUserId, Player(data))
            }

            // Find the loser to solve for changes to bounties
            players.values.forEach { s ->
                if (s.getSteamId() == data.steamUserId) {
                    s.updatePlayerData(data)
                    if (s.justLost() && s.getBounty() > 0) loserChange = s.getBounty().div(2)
                }
            }

            // Resolve changes to the winner and loser
            players.values.forEach { s ->
                if (!s.justPlayed()) s.changeBounty(0)
                if (s.getSteamId() == data.steamUserId) {
                    if (s.justWon()) {
                        gamesCount++
                        s.changeChain(1)
                        s.changeBounty((s.getChain() * s.getChain() * (100+s.getMatchesPlayed()+s.getMatchesWon())))
                        s.changeBounty(loserChange)
                    } else if (s.justLost()) {
                        s.changeChain(-1)
                        s.changeBounty((s.getChain() * s.getChain() * (10+s.getMatchesPlayed()+s.getMatchesWon())))
                        s.changeBounty(-loserChange)
                    }
                }
            }
        }
    }

}