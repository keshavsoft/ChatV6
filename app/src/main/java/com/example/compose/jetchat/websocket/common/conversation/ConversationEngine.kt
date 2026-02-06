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
    val messages = remember { mutableStateListOf<Message>() }

    LaunchedEffect(Unit) {
        AppWebSocketManager.events.collect { text ->
            Log.d(logTag, "UI received: $text")
            messages.add(
                Message(
                    author = "WS",
                    content = text,
                    timestamp = "now"
                )
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
            messages.add(Message("me", text, "now"))
            AppWebSocketManager.send(text)
        }
    )
}