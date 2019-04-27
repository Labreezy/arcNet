package twitch

/**
 * twitch.BotApi
 */
interface BotApi {

    /**
     * @return Is Xrd running and ready to serve data
     */
    fun sendMessage(message:String)

    /**
     * @return a List of the Xrd lobby's active players and their data
     */
    fun getMessages(): List<Message>

}


class Message(val comingSoon: Long)
