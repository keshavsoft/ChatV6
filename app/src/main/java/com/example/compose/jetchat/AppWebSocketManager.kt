package com.example.compose.jetchat

import android.util.Log
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

object AppWebSocketManager {
    private const val TAG = "WS_COMMON"
    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    private val _connectionState =
        MutableStateFlow<SocketConnectionState>(SocketConnectionState.Disconnected)

    val connectionState: StateFlow<SocketConnectionState> =
        _connectionState.asStateFlow()

    private val _events = MutableSharedFlow<String>(
        replay = 1,
        extraBufferCapacity = 64
    )

    val events: SharedFlow<String> = _events

    fun connect() {
        if (webSocket != null) return   // VERY IMPORTANT

        _connectionState.value = SocketConnectionState.Connecting

        val request = Request.Builder()
            .url("wss://keshavsoft.com/")
            .build()

        webSocket = client.newWebSocket(
            request,
            object : WebSocketListener() {

                override fun onOpen(ws: WebSocket, response: Response) {
                    Log.d(TAG, "Connected")
                    _connectionState.value = SocketConnectionState.Connected
                    ws.send("Connected from App")
                }

                override fun onMessage(ws: WebSocket, text: String) {
                    Log.d(TAG, "Received: $text")
                    _events.tryEmit(text)
                }

                override fun onFailure(ws: WebSocket, t: Throwable, r: Response?) {
                    Log.e(TAG, "Failure", t)
                    webSocket = null
                    _connectionState.value =
                        SocketConnectionState.Error(t.message)
                }

                override fun onClosed(ws: WebSocket, code: Int, reason: String) {
                    Log.d(TAG, "Closed: $reason")
                    webSocket = null
                    _connectionState.value = SocketConnectionState.Disconnected
                }
            }
        )
    }

    fun disconnect() {
        Log.d(TAG, "Disconnect requested by app")

        webSocket?.close(1000, "Disconnected by app")
        webSocket = null

        _connectionState.value = SocketConnectionState.Disconnected
    }

    fun send(message: String) {
        webSocket?.send(message)
    }
}