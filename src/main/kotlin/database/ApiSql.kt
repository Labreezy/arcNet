package database

import org.jdbi.v3.sqlobject.customizer.Bind

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
    fun getLegacyData(steamId: Long): LegacyData

    /**
     * Update player Legacy data on the SQL Database
     */
    fun putLegacyData(legacy: LegacyData)

    /**
     * @return all Fight archives stores in the SQL Database
     */
    fun getFightData(): List<FightData>

    /**
     * Add a new Fight to the SQL Database
     */
    fun putFightData(fight: FightData)
}

data class LegacyData(
        val steamId: Long,
        val displayName: String = "",
        val matchesWon: Int = -1,
        val matchesSum: Int = -1,
        val bountyWon: Int = -1,
        val bountySum: Int = -1
)

data class FightData(
    val winnerId: Long,
    val fallenId: Long,
    val winnerChar: Int,
    val fallenChar: Int
)
