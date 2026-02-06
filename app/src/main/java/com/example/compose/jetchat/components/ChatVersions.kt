package com.example.compose.jetchat.components

import com.example.compose.jetchat.R

data class ChatVersion(
    val id: String,        // "ws_v1", "ws_v2"
    val navId: Int,        // R.id.nav_ws_v1
    val title: String      // Drawer title
)

val CHAT_VERSIONS = listOf(
    ChatVersion(
        id = "ws_v1",
        navId = R.id.nav_ws_v1,
        title = "WebSocket V1"
    ),
    ChatVersion(
        id = "ws_v2",
        navId = R.id.nav_ws_v2,
        title = "WebSocket V2"
    )
)