package session

import memscan.MatchData
import utils.Duo
import utils.keepInRange


class Match(matchData: MatchData = MatchData(), val matchId: Long = -1L, cabinetId: Byte = -0x1) {

    private val cabinetId = cabinetId
    private var allData = hashMapOf(Pair(-1, matchData))

    // Gotten from MatchData, else gotten from LobbyData (LOBBY QUALITY DATA)
    private var playerSteamId = Duo(-1L, -1L)
    private var character = Duo(-0x1, -0x1)
    private var handle = Duo("", "")
    private var rounds = Duo(-0x1, -0x1)
    private var health = Duo(-1, -1)
    private var winner = -1

    // Gotten from MatchData, else considered useless (MATCH QUALITY DATA)
    private var matchTimer = -1
    private var tension = Duo(-1, -1)
    private var burst = Duo(false, false)
    private var isHit = Duo(false, false)
    private var risc = Duo(-1, -1)

    fun getData() = allData.values.last()

    fun updateMatchData(updatedData: MatchData): Boolean {
        if (!getData().equals(updatedData)) {

            matchTimer++
            allData.put(matchTimer, updatedData)

            playerSteamId.p1 = -1L
            character.p1 = -0x1
            handle.p1 = "P1NAME (ID ${playerSteamId.p1})"
            rounds.p1 = -0x1
            health.p1 = keepInRange(getData().health.first)//, 0, 420)
            tension.p1 = keepInRange(getData().tension.first)//, 0, 10000)
            risc.p1 = keepInRange(getData().risc.first)//, 0, 12800)
            burst.p1 = getData().burst.first
            isHit.p1 = getData().isHit.first

            playerSteamId.p2 = -1L
            character.p2 = -0x1
            handle.p2 = "P2NAME (ID ${playerSteamId.p2})"
            rounds.p2 = -0x1
            health.p2 = keepInRange(getData().health.second)//, 0, 420)
            tension.p2 = keepInRange(getData().tension.second)//, 0, 10000)
            risc.p2 = keepInRange(getData().risc.second)//, 0, 12800)
            burst.p2 = getData().burst.second
            isHit.p2 = getData().isHit.second

//            println("=====\n" +"playerSteamId ${playerSteamId.p1}\n" +
//                    "P1 character ${character.p1}\n" +
//                    "P1 handle ${handle.p1}\n" +
//                    "P1 rounds ${rounds.p1}\n" +
//                    "P1 health ${health.p1}\n" +
//                    "P1 tension ${tension.p1}\n" +
//                    "P1 risc ${risc.p1}\n" +
//                    "P1 burst ${burst.p1}\n" +
//                    "P1 isHit ${isHit.p1}\n" + "-----\n" +
//                    "P2 playerSteamId ${playerSteamId.p2}\n" +
//                    "P2 character ${character.p2}\n" +
//                    "P2 handle ${handle.p2}\n" +
//                    "P2 rounds ${rounds.p2}\n" +
//                    "P2 health ${health.p2}\n" +
//                    "P2 tension ${tension.p2}\n" +
//                    "P2 risc ${risc.p2}\n" +
//                    "P2 burst ${burst.p2}\n" +
//                    "P2 isHit ${isHit.p2}\n" + "=====\n")

            return true

        } else return false
    }

    fun getWinner():Int = winner
    fun getTimer():Int = matchTimer
    fun getHealth(side:Int):Int = health.p(side) as Int
    fun getCharacter(side:Int):Int = character.p(side) as Int
    fun getTension(side:Int):Int = tension.p(side) as Int
    fun getRisc(side:Int):Int = risc.p(side) as Int
    fun getBurst(side:Int):Boolean = burst.p(side) as Boolean
    fun getHitStun(side:Int):Boolean = isHit.p(side) as Boolean

    fun getHandleString(side:Int):String = handle.p(side) as String
    fun getHealthString(side:Int):String = "HP: ${getHealth(side)} / 420"
    fun getTensionString(side:Int):String = "Tension: ${getTension(side)} / 10000"
    fun getRiscString(side:Int):String = "   RISC: ${getRisc(side)} / 12800"
    fun getBurstString(side:Int):String = "  Burst: ${getBurst(side)}"
    fun getHitStunString(side:Int):String = "  IsHit: ${getHitStun(side)}"

    fun getCabinet():Byte = cabinetId
    fun getCabinetString(cabId:Int = getCabinet().toInt()): String {
        when(cabId) {
            0 -> return "CABINET A (SNAPSHOTS ${allData.size})"
            1 -> return "CABINET B (SNAPSHOTS ${allData.size})"
            2 -> return "CABINET C (SNAPSHOTS ${allData.size})"
            3 -> return "CABINET D (SNAPSHOTS ${allData.size})"
            else -> return "CABINET $cabId (SNAPSHOTS ${allData.size})"
        }
    }
}