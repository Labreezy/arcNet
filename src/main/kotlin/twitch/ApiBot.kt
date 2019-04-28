package twitch

/**
 * twitch.BotApi
 */
interface BotApi {

    /**
     * @return if bot is running and responsive
     */
    fun isConnected(): Boolean

    /**
     * Send a bot message to Twitch chat
     */
    fun sendMessage(message:String)

    /**
     * @return a List of all Messages
     */
    fun getMessages(): List<Message>

}


class Message(
    val twitchUserId: Long,
    val messageText: String
)
