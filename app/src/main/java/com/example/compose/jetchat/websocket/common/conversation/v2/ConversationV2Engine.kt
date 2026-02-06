package com.example.compose.jetchat.websocket.common.conversation.v2

import ConversationContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.compose.jetchat.AppWebSocketManager
import com.example.compose.jetchat.websocket.common.conversation.ConversationMessageStore
import com.example.compose.jetchat.websocket.common.conversation.ConversationUiState
import com.example.compose.jetchat.websocket.common.conversation.message.Message

@Composable
fun ConversationV2Engine(
    channelName: String,
    logTag: String,
    onNavIconPressed: () -> Unit,
    navigateToProfile: (String) -> Unit
) {

    LaunchedEffect(Unit) {

    }

    val incomingOnly = ConversationMessageStore.messages.filter { it.author == "WS" }

    val uiState = ConversationUiState(
        initialMessages = incomingOnly,
        channelName = channelName,
        channelMembers = 1
    )

    val uiState1 = ConversationUiState(
        initialMessages = ConversationMessageStore.messages,
        channelName = channelName,
        channelMembers = 1
    )

    ConversationContent(
        uiState = uiState,
        navigateToProfile = navigateToProfile,
        onNavIconPressed = onNavIconPressed,
        onMessageSent = { text ->
            AppWebSocketManager.send(text)
            ConversationMessageStore.add(
                Message("me", text, "now")
            )
        }
    )
}