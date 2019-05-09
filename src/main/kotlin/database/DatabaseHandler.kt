package database

import azUtils.getTokenFromFile
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import org.postgresql.util.PSQLException
import java.util.*

//
class DatabaseHandler(host: String, password: String, port: Int = 5432) : SqlApi {
    private val connector: Jdbi
    private val daoClass = SqlApiDao::class.java

    init {
        // DatabaseHandler's init first executes on line 14 in Session.kt
        val credentials = Properties()
        credentials["user"] = "arcNet"
        credentials["password"] = password
        try {
            connector = Jdbi.create("jdbc:postgresql://$host:$port/ArcNet", credentials)
        } catch (e: PSQLException) {
            throw RuntimeException("Could not connect to database!", e)
        }
        connector?.installPlugins()
    }

    override fun isConnected(): Boolean {
        try {
            connector.open()
        } catch(e: ConnectionException) {
            return false
        }
        return true
    }
    override fun getLegacyData(steamId: Long): LegacyData = useDao { it.getData(steamId) }

    override fun putLegacyData(legacy: LegacyData) = useDao { it.putData(legacy) }

    override fun getFightData(): List<FightData> = useDao { it.getFightData() }

    override fun putFightData(fight: FightData) = useDao { it.putFightData(fight)}

    private fun <T> useDao(callback: (dao: SqlApiDao) -> T): T =
            connector.open().use { callback.invoke(it.attach(daoClass)) }
}

interface SqlApiDao {
    @SqlQuery("select * from userData where steamId = :steamId")
    fun getData(steamId: Long): LegacyData

    @SqlUpdate(
        "insert into" +
                "  userData(steamId, displayName, matchesWon, matchesSum, bountyWon, bountySum)\n" +
                "  values (:legacy.steamId, :legacy.displayName, :legacy.matchesWon, :legacy.matchesSum, :legacy.bountyWon, :legacy.bountySum)\n" +
                "    on conflict (steamId)" +
                "       do update set" +
                "         displayName = :legacy.displayName," +
                "             matchesWon = :legacy.matchesWon," +
                "             matchesSum = :legacy.matchesSum," +
                "             bountyWon = :legacy.bountyWon," +
                "             bountySum = :legacy.bountySum" +
                "             where userData.steamId = :legacy.steamId"
    )
    fun putData(legacy: LegacyData)

    @SqlQuery("select * from fightData")
    fun getFightData(): List<FightData>

    @SqlUpdate(
            "insert into\n" +
                    "  fightData(winnerId, fallenId, winnerChar, fallenChar, occurrences)\n" +
                    "  values(:fight.winnerId, :fight.fallenId, :fight.winnerChar, :fight.fallenChar, 1)\n" +
                    "  on conflict(winnerId, fallenId, winnerChar, fallenChar)\n" +
                    "     do update set occurrences = fightData.occurrences + 1;"
    )
    fun putFightData(fight: FightData)
}
