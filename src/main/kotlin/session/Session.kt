package session

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import memscan.MemHandler
import memscan.XrdApi

class Session {
    val xrdApi: XrdApi = MemHandler()
    val players: MutableMap<Long, Player> = HashMap()
    var gameLoop = 0

    fun runGameLoop() {
        gameLoop++
        GlobalScope.launch {
            delay(256)
            if (xrdApi.isConnected()) {
                xrdApi.getPlayerData().forEach { data ->
                    if (players.containsKey(data.steamUserId)) players.get(data.steamUserId)!!.updatePlayerData(data)
                    else if (data.steamUserId != 0L) players.put(data.steamUserId, Player(data))
                }
            }
            runGameLoop()
        }
    }
}