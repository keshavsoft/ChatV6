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