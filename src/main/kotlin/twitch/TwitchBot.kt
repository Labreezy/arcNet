package twitch

import com.github.philippheuer.credentialmanager.domain.OAuth2Credential
import com.github.twitch4j.TwitchClient
import com.github.twitch4j.TwitchClientBuilder
import com.github.twitch4j.chat.events.channel.ChannelMessageEvent

class TwitchBot(accessToken: String) : BotApi {

    override fun isConnected(): Boolean {
        // TODO: Make this return the proper Boolean
        return false
    }

    private val messageCache: MutableList<Message> = mutableListOf()
    private val twitchClient: TwitchClient

    init {
        val credentials = OAuth2Credential("twitch", accessToken)
        twitchClient = TwitchClientBuilder.builder().withChatAccount(credentials)
                .withEnableChat(true)
                .withEnableHelix(true)
                .build()
        twitchClient.chat.eventManager
                .onEvent(ChannelMessageEvent::class.java)
                .subscribe {
                    messageCache.add(Message(it.user.id, it.message))
                }
    }

    override fun sendMessage(message: String) = twitchClient.chat.sendMessage("azedevs", message)

    override fun getMessages(): List<Message> = messageCache

    fun <T> eval(callback: (client: TwitchClient) -> T): T = callback.invoke(twitchClient)
}
