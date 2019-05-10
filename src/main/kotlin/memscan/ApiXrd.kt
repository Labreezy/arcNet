package memscan

import javafx.geometry.Rectangle2D
import kotlin.random.Random

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
    val steamUserId: Long,
    val displayName: String,
    val characterId: Byte,
    val cabinetLoc: Byte,
    val playerSide: Byte,
    val matchesWon: Int,
    val matchesSum: Int,
    val loadingPct: Int
    // Lobby mini-health?
    // Lobby score marks?
    // Readied up?
)

data class MatchData(
    //val players: Pair<PlayerData, PlayerData>, TBA, maybe yoink steam id through login + DB or something
    val tension: Pair<Int, Int>,
    val health: Pair<Int, Int>,
    val burst: Pair<Boolean, Boolean>,
    val risc: Pair<Int, Int>,
    val isHit: Pair<Boolean, Boolean>
    //val beats: Pair<Int, Int>,
    //val timer: Int
    // Connection? : Int
    // Score marks? : Pair<Int, Int>
    // Damage taken? : Pair<Int, Int>
    // Button(s) pressed? : Pair<?, ?>
    // Direction pressed? : Pair<?, ?>
    // Tension Pulse? : Pair<Float, Float>
    // Stun level? : Pair<Int, Int>
)

data class LobbyData(
    val lobbyName: String
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
    val userId: Long,
    val text: String
)

