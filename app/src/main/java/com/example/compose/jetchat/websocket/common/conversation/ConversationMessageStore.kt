package com.example.compose.jetchat.websocket.common.conversation

import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateListOf

object ConversationMessageStore {

    private val store =
        mutableStateMapOf<String, MutableList<Message>>() // channel → messages

    fun messages(channel: String): MutableList<Message> =
        store.getOrPut(channel) { mutableStateListOf() }

    fun add(channel: String, message: Message) {
        messages(channel).add(message)
    }
}