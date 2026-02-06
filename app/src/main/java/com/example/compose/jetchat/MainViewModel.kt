package com.example.compose.jetchat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.compose.jetchat.websocket.common.conversation.ConversationMessageStore
import com.example.compose.jetchat.websocket.common.conversation.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    init {
        viewModelScope.launch {
            AppWebSocketManager.events.collect { text ->
                ConversationMessageStore.add(
                    Message(
                        author = "WS",
                        content = text,
                        timestamp = "now"
                    )
                )
            }
        }
    }

    private val _drawerShouldBeOpened = MutableStateFlow(false)
    val drawerShouldBeOpened = _drawerShouldBeOpened.asStateFlow()

    fun openDrawer() {
        _drawerShouldBeOpened.value = true
    }

    fun resetOpenDrawerAction() {
        _drawerShouldBeOpened.value = false
    }
}