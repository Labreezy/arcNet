interface XrdApi {

    /**
     * @return Is Xrd running and ready to serve data
     */
    fun isXrdConnected(): Boolean

    /**
     * @return a List of the Xrd lobby's active players and their data
     */
    fun getXrdData(): List<PlayerData>

}

class PlayerData(
    val displayName: String,
    val steamUserId: Long,
    val characterId: Byte,
    val lobbyCabId: Byte,
    val cabSideId: Byte,
    val matchesWon: Int,
    val matchesTotal: Int,
    val loadingPct: Int)