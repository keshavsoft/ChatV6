package com.example.compose.jetchat.websocket.common.conversation

import androidx.compose.runtime.mutableStateListOf
import com.example.compose.jetchat.websocket.common.conversation.message.Message

object ConversationMessageStore {
    val messages = mutableStateListOf<Message>()
    fun add(message: Message) { messages.add(message) }
}