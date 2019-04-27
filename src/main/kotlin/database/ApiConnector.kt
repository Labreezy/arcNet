package database

import org.jdbi.v3.core.Jdbi
import java.util.*

class ApiConnector(host: String, password: String, port: Int = 5432) : SqlApi {
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
