package session

import azUtils.addCommas
import memscan.PlayerData
import session.Character.getCharacterName
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min


class Player(playerData: PlayerData = PlayerData()) {

    var present = true
    private var bounty = 0
    private var change = 0
    private var chain = 0
    private var idle = 8
    private var data = Pair(playerData, playerData)

    private fun oldData() = data.first
    fun getData() = data.second

    fun updatePlayerData(updatedData: PlayerData) {
        data = Pair(getData(), updatedData);
        if (hasLoaded()) {
            present = true
//            idle = max(session.getActivePlayerCount(), 1)
        }
    }


    fun getNameString() = "${getData().displayName}"

    fun getSteamId() = getData().steamUserId

    fun getCharacterId() = getData().characterId

    fun getCharacter() = getCharacterName(getData().characterId)

    fun isScoreboardWorthy() = getBounty() > 0 && idle > 0 && getMatchesWon() > 0

    fun getIdle() = idle

    fun isIdle() = idle <= 0

    fun incrementIdle() {
        changeBounty(0)
        if (--idle <= 0) {
            if (changeChain(-1) == 0) {
                present = false
                idle = 0
            }

        }
    }

    fun getBounty() = bounty

    fun getBountyFormatted(ramp:Float) = if (getBounty() > 0) "${addCommas(min(getBounty()-getChange()+(getChange()*ramp).toInt(), getBounty()).toString())} W$"
    else "${addCommas(max(getBounty()-getChange()+(getChange()*ramp).toInt(), getBounty()).toString())} W$"

    fun getBountyString(ramp:Float) = if (getBounty() > 0) "${getBountyFormatted(if (change!=0) ramp else 1f)}" else "Free"

    fun getRecordString() = "Chain: ${getChain()}  [ W:${getMatchesWon()} / M:${getMatchesPlayed()} ]"

    fun changeBounty(amount:Int) {
        change = amount
        bounty += amount
//        if (getChange()>0) changeCol = ColF(changeColDelay, changeColInterval, Vec4(1,1,1,0), Vec4(0.2,0.8,0.2,1))
//        else changeCol = ColF(changeColDelay+80, changeColInterval, Vec4(1,1,1,0), Vec4(0.8,0.2,0.2,1))
        if (bounty < 100) bounty = 0
    }

    fun getChain() = chain

    fun getChainString():String = if (getChain()>=8) "8 MAX" else if (getChain()>0) getChain().toString() else "-"

    fun changeChain(amount:Int): Int {
//        idle = max(session.getActivePlayerCount(), 1)
        chain += amount
        if (chain < 0) chain = 0
        if (chain > 8) chain = 8
        return chain
    }

    fun getChange() = change

    fun getChangeString(ramp:Float): String {
        if (change > 0) return "+${addCommas(min(change*ramp, change.toFloat()).toInt().toString())} W$"
        else if (change < 0) return "-${addCommas(abs(max(change*ramp, change.toFloat()).toInt()).toString())} W$"
        else return ""
    }

//    val changeColDelay = 320L
//    val changeColInterval = 16L
//    var changeCol = ColF(changeColDelay, changeColInterval, Vec4(0), Vec4(0))
//    fun getChangeColor() = changeCol.getCol()

    fun getMatchesWon() = getData().matchesWon

    fun getMatchesPlayed() = getData().matchesSum

    fun getMatchesWonString() = if (getMatchesWon()>0) "Wins: ${getMatchesWon()}" else "Wins: -"

    fun getMatchesPlayedString() = if (getMatchesPlayed()>0) "Games: ${getMatchesPlayed()}" else "Games: -"

    fun getCabinet() = getData().cabinetLoc

    fun getCabinetString(): String {
        when(getCabinet().toInt()) {
            0 -> return "Cabinet A"
            1 -> return "Cabinet B"
            2 -> return "Cabinet C"
            3 -> return "Cabinet D"
            else -> return "-"
        }
    }

    fun getPlaySideString(): String {
        when(getData().playerSide.toInt()) {
            0 -> return "Player One"
            1 -> return "Player Two"
            2 -> return "2nd (Next)"
            3 -> return "3rd"
            4 -> return "4th"
            5 -> return "5th"
            6 -> return "6th"
            7 -> return "Spectating"
            else -> return "[${getData().playerSide.toInt()}]"
        }
    }

    val MAX_IDLE = 8
    fun getStatusString() = if (idle == 0) "Idle: ${idle}   [${getLoadPercent()}%]" else "Standby: ${idle}  [${getLoadPercent()}%]"


    fun getLoadPercent() = getData().loadingPct

    fun hasLoaded() = getData().loadingPct > 0 && getData().loadingPct < 100

    fun hasPlayed() = getData().matchesSum > oldData().matchesSum

    fun hasLost() = getData().matchesWon == oldData().matchesWon && hasPlayed()

    fun hasWon() = getData().matchesWon > oldData().matchesWon && hasPlayed()

    fun getRating():Float {
        if (getMatchesPlayed() > 0) return ((((getMatchesWon().toFloat() * 0.1) * getChain()) + getMatchesWon()) / (getMatchesPlayed().toFloat())).toFloat()
        else return 0F
    }

    fun getRatingLetter(): String {
        var grade = "-"
        if (getMatchesWon() >= 1 && getRating() > 0.0f) grade  = "D"
        if (getMatchesWon() >= 1 && getRating() >= 0.1f) grade  = "D+"
        if (getMatchesWon() >= 2 && getRating() >= 0.2f) grade  = "C"
        if (getMatchesWon() >= 3 && getRating() >= 0.3f) grade  = "C+"
        if (getMatchesWon() >= 5 && getRating() >= 0.4f) grade  = "B"
        if (getMatchesWon() >= 8 && getRating() >= 0.6f) grade  = "B+"
        if (getMatchesWon() >= 13 && getRating() >= 1.0f) grade  = "A"
        if (getMatchesWon() >= 21 && getRating() >= 1.2f) grade = "A+"
        if (getMatchesWon() >= 34 && getRating() >= 1.4f) grade = "S"
        if (getMatchesWon() >= 55 && getRating() >= 1.6f) grade = "S+"

        return grade // "${grade} ${(getPlayerRating()*10).toInt()}"
    }

//    fun getRatingColor(): Vec4 {
//        var color = Col4.GHOST
//        if (getMatchesWon() >= 1 && getPlayerRating() > 0.0f) color    = Vec4(0.10, 0.90, 0.90, 1) // D
//        if (getMatchesWon() >= 1 && getPlayerRating() >= 0.1f) color   = Vec4(0.00, 0.60, 0.90, 1) // D+
//        if (getMatchesWon() >= 2 && getPlayerRating() >= 0.2f) color  = Vec4(0.20, 0.80, 0.10, 1) // C
//        if (getMatchesWon() >= 3 && getPlayerRating() >= 0.3f) color  = Vec4(0.40, 0.90, 0.10, 1) // C+
//        if (getMatchesWon() >= 5 && getPlayerRating() >= 0.4f) color  = Vec4(0.90, 0.90, 0.00, 1) // B
//        if (getMatchesWon() >= 8 && getPlayerRating() >= 0.6f) color  = Vec4(0.98, 0.64, 0.10, 1) // B+
//        if (getMatchesWon() >= 13 && getPlayerRating() >= 1.0f) color  = Vec4(0.98, 0.50, 0.00, 1) // A
//        if (getMatchesWon() >= 21 && getPlayerRating() >= 1.2f) color = Vec4(0.98, 0.25, 0.10, 1) // A+
//        if (getMatchesWon() >= 34 && getPlayerRating() >= 1.4f) color = Vec4(0.95, 0.20, 0.70, 1) // S
//        if (getMatchesWon() >= 55 && getPlayerRating() >= 1.6f) color = Vec4(0.90, 0.10, 0.95, 1) // S+
//        return color
//    }
//
//    fun getChainColor(): Vec4 {
//        when (getChain()) {
//            1 -> return Vec4(0.55,0.65,0.66,1.00) // D+
//            2 -> return Vec4(0.50,0.70,0.68,1.00) // C
//            3 -> return Vec4(0.45,0.75,0.70,1.00) // C+
//            4 -> return Vec4(0.40,0.80,0.72,1.00) // B
//            5 -> return Vec4(0.35,0.85,0.74,1.00) // B+
//            6 -> return Vec4(0.30,0.90,0.76,1.00) // A
//            7 -> return Vec4(0.25,0.95,0.78,1.00) // A+
//            8 -> return Vec4(0.20,1.00,0.80,1.00) // S
//            else -> return Col4.GHOST
//        }
//    }

}

