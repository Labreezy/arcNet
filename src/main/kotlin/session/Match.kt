package session

import memscan.MatchData

/*
    data class MatchData(
        //val players: Pair<PlayerData, PlayerData>, TBA, maybe yoink steam id through login + DB or something

        val tension: Pair<Int, Int> = Pair(-1,-1),
        val health: Pair<Int, Int> = Pair(-1,-1),
        val burst: Pair<Boolean, Boolean> = Pair(false,false),
        val risc: Pair<Int, Int> = Pair(-1,-1),
        val isHit: Pair<Boolean, Boolean> = Pair(false,false)

        // val beats: Pair<Int, Int>,
        // val timer: Int
        // Connection? : Int
        // Score marks? : Pair<Int, Int>
        // Damage taken? : Pair<Int, Int>
        // Button(s) pressed? : Pair<?, ?>
        // Direction pressed? : Pair<?, ?>
        // Tension Pulse? : Pair<Float, Float>
        // Stun level? : Pair<Int, Int>
    )
*/

class Match(matchData: MatchData = MatchData()) {

//    private var p1data = ArrayList<Snap>()
//    private var p2data = ArrayList<Snap>()
    private var data = Pair(matchData, matchData)

    private fun oldData() = data.first
    fun getData() = data.second

    fun updateMatchData(updatedData: MatchData) {
        data = Pair(getData(), updatedData)
    }

//    class Snap(updatedData: MatchData) {
//
//    }

}