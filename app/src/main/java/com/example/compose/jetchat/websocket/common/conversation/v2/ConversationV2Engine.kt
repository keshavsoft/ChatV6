package com.example.compose.jetchat.websocket.common.conversation.v2

import ConversationContent
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.example.compose.jetchat.AppWebSocketManager
import com.example.compose.jetchat.websocket.common.conversation.ConversationMessageStore
import com.example.compose.jetchat.websocket.common.conversation.ConversationUiState
import com.example.compose.jetchat.websocket.common.conversation.Message

@Composable
fun ConversationV2Engine(
    channelName: String,
    logTag: String,
    onNavIconPressed: () -> Unit,
    navigateToProfile: (String) -> Unit
) {

    LaunchedEffect(Unit) {
        AppWebSocketManager.events.collect { text ->
            Log.d(logTag, "UI received: $text")
            ConversationMessageStore.add(
                Message("WS", text, "now")
            )
        }
    }

    val uiState = ConversationUiState(
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