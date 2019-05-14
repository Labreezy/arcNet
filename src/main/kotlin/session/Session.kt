package session

import database.DatabaseHandler
import memscan.MemHandler
import memscan.PlayerData
import memscan.XrdApi
import tornadofx.Controller


class Session: Controller() {
    val xrdApi: XrdApi = MemHandler()
    val dataApi: DatabaseHandler = DatabaseHandler("159.89.112.213", password = "", username = "")
    val players: HashMap<Long, Player> = HashMap()
    var gamesCount: Int = 0

    fun updatePlayers(): Boolean {
        var somethingChanged = false
        var loserChange = 0
        val playerData = xrdApi.getPlayerData()
        playerData.forEach { data ->
            if (data.steamUserId != 0L && !players.containsKey(data.steamUserId)) {
                players.put(data.steamUserId, Player(data))
                somethingChanged = true
            }
            if (players.containsKey(data.steamUserId) && !players.get(data.steamUserId)!!.getData().equals(data)) {
                players.get(data.steamUserId)!!.updatePlayerData(data)
                somethingChanged = true
            }
            val currLoser = resolveTheLoser(data)
            if (currLoser > 0) {
                loserChange = currLoser
                somethingChanged = true
            }
        }
        playerData.forEach { resolveTheWinner(it, loserChange) }
        return somethingChanged
    }

    private fun addPlayerIfNew(data: PlayerData) {
        if (!players.containsKey(data.steamUserId) && data.steamUserId != 0L) {
            players[data.steamUserId] = Player(data)
        }
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

    fun getActivePlayerCount() = players.values.filter { !it.isIdle() }.size

}
