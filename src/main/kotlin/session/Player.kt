package session

import classes.Character.getCharacterName
import classes.addCommas
import memscan.PlayerData
import kotlin.math.abs

class Player(playerData: PlayerData) {

    private val MAX_IDLE = 8
    var present = true
    private var bounty = 0
    private var change = 0
    private var chain = 0
    private var idle = MAX_IDLE
    private var data = Pair(playerData, playerData)

    private fun oldData() = data.first
    fun getData() = data.second

    fun updatePlayerData(updatedData: PlayerData) { data = Pair(getData(), updatedData); present = true }

    fun getDisplayName() = getData().displayName

    fun getNameString() = "${getDisplayName()}"

    fun getSteamId() = getData().steamUserId

    fun getCharacter(shortened: Boolean) = getCharacterName(getData().characterId, shortened)

    fun isScoreboardWorthy() = getBounty() > 0 && idle > 0 && getMatchesWon() > 0

    fun getIdle() = idle

    fun incrementIdle() {
        changeBounty(0)
        if (--idle <= 0) {
            idle = 0
            if (changeChain(-1) <= 0) present = false
            else idle = MAX_IDLE
        }
    }

    fun getBounty() = bounty

    fun getBountyFormatted() = "${addCommas(getBounty().toString())} W$"

    fun getBountyString() = if (getBounty() > 0) "Bounty: ${getBountyFormatted()}  ${if (change>0) "(+${change})" else if (change<0) "(${change})" else ""}" else "Civilian"

    fun getChain() = chain

    fun getChainString():String = "Chains: ${if (getChain()>0) getChain() else "-"}"

    fun changeChain(amount:Int): Int {
        chain += amount
        if (chain < 0) chain = 0
        if (chain > 8) chain = 8
        return chain
    }

    fun getChangeString(): String {
        if (change > 0) return "+${addCommas(change.toString())} W$\n "
        else if (change < 0) return " \n-${addCommas(abs(change).toString())} W$"
        else return " \n "
    }

    fun getMatchesWon() = getData().matchesWon

    fun getMatchesPlayed() = getData().matchesSum

    fun getRecordString() = "Wins: ${getMatchesWon()} / Games: ${getMatchesPlayed()}"

    fun getCabinet() = getData().cabinetLoc

    fun getCabinetString(): String {
        when(getCabinet().toInt()) {
            0 -> return "Cab A - ${getPlaySideString()}"
            1 -> return "Cab B - ${getPlaySideString()}"
            2 -> return "Cab C - ${getPlaySideString()}"
            3 -> return "Cab D - ${getPlaySideString()}"
            else -> return "Roaming..."
        }
    }

    private fun getPlaySideString(): String {
        if (getCabinet().toInt() <= 3 && getCabinet().toInt() >= 0) {
            when(getData().playerSide.toInt()) {
                0 -> return "Player One"
                1 -> return "Player Two"
                2 -> return "Second"
                3 -> return "Third"
                4 -> return "Fourth"
                5 -> return "Fifth"
                6 -> return "Sixth"
                7 -> return "Spectating"
                else -> return "[${getData().playerSide.toInt()}]"
            }
        }
        return "-"
    }

    fun getLoadPercent() = getData().loadingPct

    fun justPlayed() = getData().matchesSum > oldData().matchesSum

    fun justLost() = getData().matchesWon == oldData().matchesWon && justPlayed()

    fun justWon() = getData().matchesWon > oldData().matchesWon && justPlayed()

    fun getRating() = (getMatchesWon() + getChain()).toFloat() / (getMatchesPlayed() - getChain()).toFloat()

    fun changeBounty(amount:Int) {
        change = amount
        bounty += amount
        if (bounty < 0) bounty = 0
    }

    fun getRatingLetter(): String {
        var grade = "-"
        if (getMatchesWon() > 0) {
            grade = "D"
            val gradeConversion = getRating()
            if (gradeConversion >= 0.1f) grade = "D+"
            if (gradeConversion >= 0.2f) grade = "C"
            if (gradeConversion >= 0.3f) grade = "C+"
            if (gradeConversion >= 0.4f) grade = "B"
            if (getMatchesWon() >= 4 && gradeConversion >= 0.6f) grade = "B+"
            if (getMatchesWon() >= 8 && gradeConversion >= 1.0f) grade = "A"
            if (getMatchesWon() >= 16 && gradeConversion >= 1.5f) grade = "A+"
            if (getMatchesWon() >= 32 && gradeConversion >= 2.0f) grade = "S"
            if (getMatchesWon() >= 64 && gradeConversion >= 3.0f) grade = "S+"
        }
        return grade
    }

}

