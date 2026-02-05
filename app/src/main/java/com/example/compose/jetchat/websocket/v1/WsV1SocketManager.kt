package com.example.compose.jetchat.websocket.v1

import android.util.Log
import okhttp3.*

class WsV1SocketManager(
    private val onMessage: (String) -> Unit
) {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect() {
        val request = Request.Builder()
            .url("wss://keshavsoft.com/")
            .build()

        webSocket = client.newWebSocket(request, listener)
    }

    fun send(message: String) {
        webSocket?.send(message)
    }

    private val listener = object : WebSocketListener() {

        override fun onOpen(webSocket: WebSocket, response: Response) {
            Log.d("WS_V1", "Connected")
            webSocket.send("Hello from Android")
        }

        override fun onMessage(webSocket: WebSocket, text: String) {
            Log.d("WS_V1", "Received: $text")
            onMessage(text)
        }

        override fun onFailure(
            webSocket: WebSocket,
            t: Throwable,
            response: Response?
        ) {
            Log.e("WS_V1", "Error", t)
        }
    }
}