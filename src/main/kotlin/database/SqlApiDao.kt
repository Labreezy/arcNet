package database

import org.jdbi.v3.sqlobject.statement.SqlQuery
import org.jdbi.v3.sqlobject.statement.SqlUpdate

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
}
