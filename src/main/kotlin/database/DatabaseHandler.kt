package database

import org.jdbi.v3.core.Jdbi
import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate
import java.util.*

class DatabaseHandler(host: String, password: String, port: Int = 5432) : SqlApi {
    private val connector: Jdbi
    private val daoClass = SqlApiDao::class.java

    init {
        val credentials = Properties()
        credentials["user"] = "arcNet"
        credentials["password"] = password
        connector = Jdbi.create("jdbc:postgresql://$host:$port/ArcNet", credentials)
        connector.installPlugins()
    }

    override fun isConnected(): Boolean = connector.open().use { it.connection.isValid(5) }

    override fun getData(steamId: Long): LegacyData = useDao { it.getData(steamId) }

    override fun putData(legacy: LegacyData) = useDao { it.putData(legacy) }

    private fun <T> useDao(callback: (dao: SqlApiDao) -> T): T =
            connector.open().use { callback.invoke(it.attach(daoClass)) }
}

interface SqlApiDao {
    @SqlQuery("select * from userData where steamUserId = :steamUserId")
    fun getData(steamId: Long): LegacyData

    @SqlUpdate(
        "insert into" +
                "  userData(steamUserId, displayName, matchesWon, matchesSum, bountyWon, bountySum)\n" +
                "  values (:legacy.steamUserId, :legacy.displayName, :legacy.matchesWon, :legacy.matchesSum, :legacy.bountyWon, :legacy.bountySum)\n" +
                "    on conflict (steamUserId)" +
                "       do update set" +
                "         displayName = :legacy.displayName," +
                "             matchesWon = :legacy.matchesWon," +
                "             matchesSum = :legacy.matchesSum," +
                "             bountyWon = :legacy.bountyWon," +
                "             bountySum = :legacy.bountySum" +
                "             where userData.steamUserId = :legacy.steamUserId"
    )
    fun putData(legacy: LegacyData)
}