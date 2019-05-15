package session

import database.DatabaseHandler
import memscan.MemHandler
import memscan.PlayerData
import memscan.XrdApi
import tornadofx.Controller
import kotlin.math.max


class Session: Controller() {
    val xrdApi: XrdApi = MemHandler()
    val dataApi: DatabaseHandler = DatabaseHandler("159.89.112.213", password = "", username = "")
    val players: HashMap<Long, Player> = HashMap()
    val matches: HashMap<Long, Match> = HashMap()
    var totalMatchesPlayed: Int = 0

    fun updatePlayers(): Boolean {
        var somethingChanged = false
        var bountyReward = 0
        val playerData = xrdApi.getPlayerData()

        playerData.forEach { data ->
            if (data.steamUserId != 0L) {
                // Add player if they aren't already stored
                if (!players.containsKey(data.steamUserId)) {
                    players[data.steamUserId] = Player(data); somethingChanged = true }

                // The present is now the past, and the future is now the present
                val player = players[data.steamUserId]!!
                if (!player.getData().equals(data)) { somethingChanged = true }
                player.updatePlayerData(data, getActivePlayerCount())

                // Resolve if a game occured and what the reward will be
                val bountyLost = resolveEveryoneElse(data)
                if (bountyLost > 0) { bountyReward = bountyLost; somethingChanged = true }
            }
        }

        // Pay the winner
        playerData.forEach { resolveTheWinner(it, bountyReward) }

        return somethingChanged
    }

    private fun resolveTheWinner(data: PlayerData, loserChange: Int) {
        players.values.filter { it.getSteamId() == data.steamUserId && it.hasWon() }.forEach { w ->
            w.changeChain(1)
            val payout = w.getChain() * w.getMatchesWon() + w.getMatchesPlayed() + loserChange + (w.getChain() * w.getChain() * 100)
            w.changeBounty(payout)
            totalMatchesPlayed++
        }
    }

    private fun resolveEveryoneElse(data: PlayerData): Int {
        var loserChange = 0
        players.values.filter { it.getSteamId().equals(data.steamUserId) && it.hasLost() }.forEach { l ->
            players.values.forEach { p -> if (!p.hasPlayed()) p.incrementIdle() }
            l.changeChain(-1)
            if (l.getBounty() > 0) loserChange = l.getBounty().div(3)
            l.changeBounty(-loserChange)
            return loserChange
        }
        return 0
    }

    fun getActivePlayerCount() = max(players.values.filter { !it.isIdle() }.size, 1)

}
