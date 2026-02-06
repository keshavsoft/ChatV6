package com.example.compose.jetchat.websocket.common.conversation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import com.example.compose.jetchat.AppWebSocketManager

@Composable
fun ConversationEngine(
    channelName: String,
    logTag: String,
    onNavIconPressed: () -> Unit,
    navigateToProfile: (String) -> Unit
) {
    val messages = ConversationMessageStore.messages(channelName)

    LaunchedEffect(Unit) {
        AppWebSocketManager.events.collect { text ->
            Log.d(logTag, "UI received: $text")

            ConversationMessageStore.add(
                channelName,
                Message("WS", text, "now")
            )
        }
    }

    val uiState = ConversationUiState(
        initialMessages = messages,
        channelName = channelName,
        channelMembers = 1
    )

    ConversationContent(
        uiState = uiState,
        navigateToProfile = navigateToProfile,
        onNavIconPressed = onNavIconPressed,
        onMessageSent = { text ->
            ConversationMessageStore.add(
                channelName,
                Message("me", text, "now")
            )
            AppWebSocketManager.send(text)
        }
    )
}