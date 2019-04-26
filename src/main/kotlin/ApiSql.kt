/**
 * SqlApi
 * provides [LegacyData]
 */
interface SqlApi {

    /**
     * @return SQL is running and ready to serve data
     */
    fun isConnected(): Boolean

    /**
     * @return player Legacy data from the SQL Database
     */
    fun getData(steamId: Long): LegacyData

    /**
     * Update player Legacy data on the SQL Database
     */
    fun putData(legacy: LegacyData)
}


class LegacyData(
    val steamUserId: Long,
    val displayName: String,
    val matchesWon: Int,
    val matchesSum: Int,
    val bountyWon: Int,
    val bountySum: Int
)