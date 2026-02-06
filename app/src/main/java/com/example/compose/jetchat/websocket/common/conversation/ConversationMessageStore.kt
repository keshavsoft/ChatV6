package com.example.compose.jetchat.websocket.common.conversation

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateListOf

object ConversationMessageStore {
    val messages = mutableStateListOf<Message>()
    fun add(message: Message) = messages.add(message)
}