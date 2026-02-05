package com.example.compose.jetchat

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

object AppWebSocketManager {

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    private val _events1 = MutableSharedFlow<String>(extraBufferCapacity = 64)

    private val _events = MutableSharedFlow<String>(
        replay = 1,
        extraBufferCapacity = 64
    )

    val events: SharedFlow<String> = _events

    fun connect() {
        if (webSocket != null) return   // VERY IMPORTANT

        val request = Request.Builder()
            .url("wss://keshavsoft.com/")
            .build()

        webSocket = client.newWebSocket(
            request,
            object : WebSocketListener() {

                override fun onOpen(ws: WebSocket, response: Response) {
                    Log.d("WS_COMMON", "Connected")
                    ws.send("Connected from App")
                }

                override fun onMessage(ws: WebSocket, text: String) {
                    Log.d("WS_COMMON", "Received: $text")
                    _events.tryEmit(text)
                }

                override fun onFailure(ws: WebSocket, t: Throwable, r: Response?) {
                    webSocket = null
                }
            }
        )
    }

    fun send(message: String) {
        webSocket?.send(message)
    }
}