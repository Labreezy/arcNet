package memscan

/**
 * memscan.XrdApi
 * provides [PlayerData]
 * provides [MatchData]
 */
interface XrdApi {

    /**
     * @return Is Xrd running and ready to serve data
     */
    fun isConnected(): Boolean

    /**
     * @return a List of the Xrd lobby's active players and their data
     */
    fun getPlayerData(): List<PlayerData>

    /**
     * @return data from current match
     */
    fun getMatchData(): MatchData

    /**
     * @return data from current lobby
     */
    fun getLobbyData(): LobbyData

}

data class PlayerData(
    val steamUserId: Long = -1L,
    val displayName: String = "",
    val characterId: Byte = -0x1,
    val cabinetLoc: Byte = -0x1,
    val playerSide: Byte = -0x1,
    val matchesWon: Int = -1,
    val matchesSum: Int = -1,
    val loadingPct: Int = -1
    // Lobby mini-health?
    // Lobby score marks?
    // Readied up?
) {
    fun equals(other: PlayerData) = other.displayName.equals(displayName) &&
                other.characterId == characterId &&
                other.cabinetLoc == cabinetLoc &&
                other.playerSide == playerSide &&
                other.matchesWon == matchesWon &&
                other.matchesSum == matchesSum &&
                other.loadingPct == loadingPct
}

data class MatchData(
    //val players: Pair<PlayerData, PlayerData>, TBA, maybe yoink steam id through login + DB or something
    val tension: Pair<Int, Int> = Pair(-1,-1),
    val health: Pair<Int, Int> = Pair(-1,-1),
    val burst: Pair<Boolean, Boolean> = Pair(false,false),
    val risc: Pair<Int, Int> = Pair(-1,-1),
    val isHit: Pair<Boolean, Boolean> = Pair(false,false),
    //val beats: Pair<Int, Int>,
    val timer: Int = -1,
    val rounds: Pair<Int, Int>= Pair(-1,-1)
    // Connection? : Int
    // Score marks? : Pair<Int, Int>
    // Damage taken? : Pair<Int, Int>
    // Button(s) pressed? : Pair<?, ?>
    // Direction pressed? : Pair<?, ?>
    // Tension Pulse? : Pair<Float, Float>
    // Stun level? : Pair<Int, Int>
) {
    fun equals(other: MatchData) = other.tension == tension &&
            other.health == health &&
            other.burst == burst &&
            other.risc == risc &&
            other.isHit == isHit &&
            other.timer == timer &&
            other.rounds == rounds
            other.isHit == isHit

}

data class LobbyData(
    val lobbyName: String = ""
    // Open cabinets? : Int
    // Points for win? : Int
    // Time per match? : Int
    // Passworded? : Boolean
    // Winner/Loser Stays? : Int
    // Quality restriction? : Int
    // Serious/Casual match? : Boolean
    // Chat messages? : List<LobbyMessage>
)

class LobbyMessage(
    val userId: Long = -1L,
    val text: String = ""
)

