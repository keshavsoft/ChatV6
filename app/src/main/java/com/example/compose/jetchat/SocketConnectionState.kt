package com.example.compose.jetchat

sealed interface SocketConnectionState {
    object Connecting : SocketConnectionState
    object Connected : SocketConnectionState
    object Disconnected : SocketConnectionState
    data class Error(val reason: String? = null) : SocketConnectionState
}