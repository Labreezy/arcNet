package session

import classes.Character.getCharacterName
import classes.addCommas
import glm_.vec4.Vec4
import memscan.PlayerData
import kotlin.math.abs

class Player(playerData: PlayerData) {

    private val MAX_IDLE = 8
    var present = true
    private var bounty = 0
    private var change = 0
    private var chain = 0
    private var idle = 8
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

    fun getChainString():String = "${if (getChain()>0) getChain() else "-"}"

    fun changeChain(amount:Int): Int {
        idle = MAX_IDLE
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
            0 -> return "${getPlaySideString()} on A"
            1 -> return "${getPlaySideString()} on B"
            2 -> return "${getPlaySideString()} on C"
            3 -> return "${getPlaySideString()} on D"
            else -> return "-"
        }
    }

    private fun getPlaySideString(): String {
        if (getCabinet().toInt() <= 3 && getCabinet().toInt() >= 0) {
            when(getData().playerSide.toInt()) {
                0 -> return "Player One"
                1 -> return "Player Two"
                2 -> return "Next"
                3 -> return "Spot 3"
                4 -> return "Spot 4"
                5 -> return "Spot 5"
                6 -> return "Spot 6"
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

    fun getRating():Float {
        if (getMatchesPlayed() > 0) return ((((getMatchesWon().toFloat() * 0.1) * getChain()) + getMatchesWon()) / (getMatchesPlayed().toFloat())).toFloat()
        else return 0F
    }

    fun changeBounty(amount:Int) {
        change = amount
        bounty += amount
        if (bounty < 0) bounty = 0
    }

    fun getRatingLetter(): String {
        var grade = "-"
        if (getMatchesWon() > 0 && getRating() > 0.0f) grade  = "D"
        if (getMatchesWon() > 0 && getRating() >= 0.1f) grade  = "D+"
        if (getMatchesWon() >= 1 && getRating() >= 0.2f) grade  = "C"
        if (getMatchesWon() >= 1 && getRating() >= 0.3f) grade  = "C+"
        if (getMatchesWon() >= 2 && getRating() >= 0.4f) grade  = "B"
        if (getMatchesWon() >= 4 && getRating() >= 0.6f) grade  = "B+"
        if (getMatchesWon() >= 8 && getRating() >= 1.0f) grade  = "A"
        if (getMatchesWon() >= 16 && getRating() >= 1.5f) grade = "A+"
        if (getMatchesWon() >= 32 && getRating() >= 2.0f) grade = "S"
        if (getMatchesWon() >= 64 && getRating() >= 3.0f) grade = "S+"

        return grade // "${grade} ${(getRating()*10).toInt()}"
    }

    fun getRatingColor(): Vec4 {
        var color = Vec4(0.8,0.8,0.8,0.8)
        if (getMatchesWon() > 0 && getRating() > 0.0f) color  = Vec4(0.0, 0.6, 0.8, 1) // D
        if (getMatchesWon() > 0 && getRating() >= 0.1f) color  = Vec4(0.1, 0.7, 0.6, 1) // D+
        if (getMatchesWon() >= 1 && getRating() >= 0.2f) color  = Vec4(0.2, 0.8, 0.4, 1) // C
        if (getMatchesWon() >= 1 && getRating() >= 0.3f) color  = Vec4(0.3, 0.9, 0.2, 1) // C+
        if (getMatchesWon() >= 2 && getRating() >= 0.4f) color  = Vec4(0.4, 1.0, 0.1, 1) // B
        if (getMatchesWon() >= 4 && getRating() >= 0.6f) color  = Vec4(0.5, 0.8, 0.1, 1) // B+
        if (getMatchesWon() >= 8 && getRating() >= 1.0f) color  = Vec4(0.6, 0.6, 0.2, 1) // A
        if (getMatchesWon() >= 16 && getRating() >= 1.5f) color = Vec4(0.7, 0.4, 0.4, 1) // A+
        if (getMatchesWon() >= 32 && getRating() >= 2.0f) color = Vec4(0.8, 0.2, 0.7, 1) // S
        if (getMatchesWon() >= 64 && getRating() >= 3.0f) color = Vec4(0.9, 0.0, 0.8, 1) // S+
        return color
    }

}

