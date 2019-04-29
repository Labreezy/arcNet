package session

import application.getSession
import classes.Character.getCharacterName
import classes.addCommas
import glm_.vec4.Vec4
import memscan.PlayerData
import kotlin.math.abs
import kotlin.math.max

class Player(playerData: PlayerData) {

    var present = true
    private var bounty = 0
    private var change = 0
    private var chain = 0
    private var idle = 1
    private var data = Pair(playerData, playerData)

    private fun oldData() = data.first
    fun getData() = data.second

    fun updatePlayerData(updatedData: PlayerData) {
        data = Pair(getData(), updatedData);
        if (hasLoaded()) {
            present = true
            idle = max(getSession().getActivePlayerCount(), 1)
        }
    }

    fun getDisplayName() = getData().displayName

    fun getNameString() = "${getDisplayName()}"

    fun getSteamId() = getData().steamUserId

    fun getCharacter(shortened: Boolean) = getCharacterName(getData().characterId, shortened)

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

    fun getBountyFormatted() = "${addCommas(getBounty().toString())} W$"

    fun getBountyString() = if (getBounty() > 0) "Bounty: ${getBountyFormatted()}" else "Free"

    fun changeBounty(amount:Int) {
        change = amount
        bounty += amount
        if (bounty < 100) bounty = 0
    }

    fun getChain() = chain

    fun getChainString():String = "${if (getChain()>0) getChain() else if (getChain()>=8) "MAX" else "-"}"

    fun changeChain(amount:Int): Int {
        // Amount replenished for idle is equal to active players
        idle = max(getSession().getActivePlayerCount(), 1)
        chain += amount
        if (chain < 0) chain = 0
        if (chain > 8) chain = 8
        return chain
    }

    fun getChange() = change

    fun getChangeString(): String {
        if (change > 0) return "+${addCommas(change.toString())} W$"
        else if (change < 0) return "-${addCommas(abs(change).toString())} W$"
        else return ""
    }

    fun getMatchesWon() = getData().matchesWon

    fun getMatchesPlayed() = getData().matchesSum

    fun getRecordString() = "Wins: ${getMatchesWon()} / Games: ${getMatchesPlayed()}"

    fun getCabinet() = getData().cabinetLoc

    fun getCabinetString(): String {
        when(getCabinet().toInt()) {
            0 -> return "${getPlaySideString()} on Alfa"
            1 -> return "${getPlaySideString()} on Bravo"
            2 -> return "${getPlaySideString()} on Charlie"
            3 -> return "${getPlaySideString()} on Delta"
            else -> return "-"
        }
    }

    private fun getPlaySideString(): String {
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
        if (getMatchesWon() > 0 && getRating() > 0.0f) grade  = "D"
        if (getMatchesWon() > 0 && getRating() >= 0.1f) grade  = "D+"
        if (getMatchesWon() >= 1 && getRating() >= 0.2f) grade  = "C"
        if (getMatchesWon() >= 1 && getRating() >= 0.3f) grade  = "C+"
        if (getMatchesWon() >= 2 && getRating() >= 0.4f) grade  = "B"
        if (getMatchesWon() >= 4 && getRating() >= 0.6f) grade  = "B+"
        if (getMatchesWon() >= 8 && getRating() >= 1.0f) grade  = "A"
        if (getMatchesWon() >= 16 && getRating() >= 1.2f) grade = "A+"
        if (getMatchesWon() >= 32 && getRating() >= 1.4f) grade = "S"
        if (getMatchesWon() >= 64 && getRating() >= 1.6f) grade = "S+"

        return grade // "${grade} ${(getRating()*10).toInt()}"
    }

    fun getRatingColor(): Vec4 {
        var color = Vec4(1,1,1,0.4)
        if (getMatchesWon() > 0 && getRating() > 0.0f) color    = Vec4(0.10, 0.90, 0.90, 1) // D
        if (getMatchesWon() > 0 && getRating() >= 0.1f) color   = Vec4(0.00, 0.60, 0.90, 1) // D+
        if (getMatchesWon() >= 1 && getRating() >= 0.2f) color  = Vec4(0.20, 0.80, 0.10, 1) // C
        if (getMatchesWon() >= 1 && getRating() >= 0.3f) color  = Vec4(0.40, 0.90, 0.10, 1) // C+
        if (getMatchesWon() >= 2 && getRating() >= 0.4f) color  = Vec4(0.90, 0.90, 0.00, 1) // B
        if (getMatchesWon() >= 4 && getRating() >= 0.6f) color  = Vec4(0.98, 0.70, 0.00, 1) // B+
        if (getMatchesWon() >= 8 && getRating() >= 1.0f) color  = Vec4(0.98, 0.50, 0.00, 1) // A
        if (getMatchesWon() >= 16 && getRating() >= 1.2f) color = Vec4(0.98, 0.25, 0.10, 1) // A+
        if (getMatchesWon() >= 32 && getRating() >= 1.4f) color = Vec4(0.95, 0.20, 0.70, 1) // S
        if (getMatchesWon() >= 64 && getRating() >= 1.6f) color = Vec4(0.90, 0.10, 0.95, 1) // S+
        return color
    }

}

