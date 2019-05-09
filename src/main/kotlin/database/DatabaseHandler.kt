package database

import org.jdbi.v3.core.ConnectionException
import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.*

//Soon to be Deprecated in favor of Web API
class DatabaseHandler(host: String, password: String, username: String = "arcNet", port: Int = 5432) : SqlApi {
    private val connector: Jdbi
    private val daoClass = SqlApiDao::class.java

    init {
        // DatabaseHandler's init first executes on line 14 in Session.kt
        //Init credentials
        val credentials = Properties()
        credentials["user"] = username
        credentials["password"] = password
        //Login
        connector = Jdbi.create("jdbc:postgresql://$host:$port/ArcNet", credentials)
        //Install kotlin extensions, etc.
        connector.installPlugins()
        //CREATES DATA TABLES IF DOESNT EXIST
        if(isConnected()) {
            connector.open().use {
                //CREATES fightData TABLE IF DOESNT EXIST
                it.execute("create table if not exists fightdata (winnerid bigint, winnerchar smallint, fallenid bigint, fallenchar smallint, occurences int, unique(winnerid, winnerchar, fallenid, fallenchar))")
                //CREATES userData TABLE IF DOESNT EXIST
                it.execute("create table if not exists userdata(id bigint unique, displayname text, matcheswon int, matchessum int, bountywon int, bountysum int)")
            }
        }
    }

    override fun isConnected(): Boolean {
        return try {
            connector.open().use { true }
        } catch(e: ConnectionException) {
            false
        }
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
            "insert into " +
                    "fightData(winnerId, fallenId, winnerChar, fallenChar) " +
                    "values(:fight.winnerId, :fight.fallenId, :fight.winnerChar, :fight.fallenChar) " +
                    "on conflict do nothing"
    )
    fun putFightData(fight: FightData)
}
