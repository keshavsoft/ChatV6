package com.example.compose.jetchat

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext

@Composable
fun WebSocketConnectionToast() {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        AppWebSocketManager.connectionState.collect { state ->
            when (state) {
                SocketConnectionState.Connected ->
                    Toast.makeText(context, "WebSocket connected", Toast.LENGTH_SHORT).show()

                SocketConnectionState.Disconnected ->
                    Toast.makeText(context, "WebSocket disconnected", Toast.LENGTH_SHORT).show()

                else -> Unit
            }
        }
    }
}