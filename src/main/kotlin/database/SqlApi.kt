package database

/**
 * database.SqlApi
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

data class LegacyData(
    val steamId: Long,
    val displayName: String = "",
    val matchesWon: Int = -1,
    val matchesSum: Int = -1,
    val bountyWon: Int = -1,
    val bountySum: Int = -1
)
