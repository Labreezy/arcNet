package session

import memscan.MatchData


class Match(matchData: MatchData = MatchData()) {

    private var timeId = 0
    private var allData = ArrayList<MatchData>()

    init { allData.add(matchData) }

    fun getData() = allData[allData.size-1]

    fun updateMatchData(updatedData: MatchData) {
        if (!getData().equals(updatedData)) allData.add(updatedData)
        timeId++
    }

    fun getHealth(side:Int):Int = if (side==0) getData().health.first else getData().health.second
    fun getTension(side:Int):Int = if (side==0) getData().tension.first else getData().tension.second
    fun getRisc(side:Int):Int = if (side==0) getData().risc.first else getData().risc.second
    fun getBurst(side:Int):Boolean = if (side==0) getData().burst.first else getData().burst.second
    fun getHitStun(side:Int):Boolean = if (side==0) getData().isHit.first else getData().isHit.second

}