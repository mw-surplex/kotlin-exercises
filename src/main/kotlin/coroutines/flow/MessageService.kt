package coroutines.flow

import kotlinx.coroutines.flow.*

class MessageService(
    private val messageRepository: MessageRepository
) {
    fun threadsSearch(
        query: Flow<String>
    ): Flow<MessageThread> = TODO()

    fun subscribeThreads(
        threads: Flow<MessageThread>
    ): Flow<MessageThreadUpdate> = TODO()

    fun sendMessages(
        messages: Flow<List<Message>>
    ): Flow<MessageSendingResponse> = TODO()
}

interface MessageRepository {
    fun searchThreads(
        query: String
    ): Flow<MessageThread>

    fun subscribeThread(
        threadId: String
    ): Flow<MessageThreadUpdate>

    fun sendMessages(
        messages: List<Message>
    ): Flow<MessageSendingResponse>
}

data class MessageThread(val id: String, val name: String)
data class MessageThreadUpdate(val threadId: String, val messages: List<Message>)
data class Message(val senderId: String, val text: String, val threadId: String)
data class MessageSendingResponse(val messageId: String, val success: Boolean)

open class OpenMessageRepository : MessageRepository {
    override fun searchThreads(query: String): Flow<MessageThread> {
        TODO()
    }

    override fun subscribeThread(threadId: String): Flow<MessageThreadUpdate> {
        TODO()
    }

    override fun sendMessages(messages: List<Message>): Flow<MessageSendingResponse> {
        TODO()
    }
}
